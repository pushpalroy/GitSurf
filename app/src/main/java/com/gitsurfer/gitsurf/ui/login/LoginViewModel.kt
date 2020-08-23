package com.gitsurfer.gitsurf.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkManager
import com.gitsurfer.gitsurf.data.network.models.request.AuthRequestModel
import com.gitsurfer.gitsurf.data.network.models.response.BasicToken
import com.gitsurfer.gitsurf.data.network.models.response.User
import com.gitsurfer.gitsurf.data.network.models.response.toRoomUser
import com.gitsurfer.gitsurf.utils.exceptions.ValidationException
import com.gitsurfer.gitsurf.data.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.utils.constants.APPLICATION_ID
import com.gitsurfer.gitsurf.utils.constants.CALLBACK_URL
import com.gitsurfer.gitsurf.utils.constants.CLIENT_ID
import com.gitsurfer.gitsurf.utils.constants.CLIENT_SECRET
import com.gitsurfer.gitsurf.utils.constants.TOKEN_PREFIX
import kotlinx.coroutines.launch
import okhttp3.Credentials
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
  private val appRepository: AppRepository,
  private val networkManager: NetworkManager,
  private val sharedPrefUtils: SharedPrefUtils
) : BaseViewModel() {

  companion object {
    private const val TAG = "Login"
  }

  private val _userLoggedInLiveData = MutableLiveData<Boolean>()
  val userLoggedInLiveData: LiveData<Boolean>
    get() = _userLoggedInLiveData

  var user: User? = null

  var username: String? = ""
  var password: String? = ""

  fun loginUser() {
    try {
      isValidForm()
      basicAuth(username, password)
    } catch (exception: ValidationException) {
      updateExceptionLiveData(exception)
    }
  }

  @Throws(ValidationException::class)
  private fun isValidForm(): Boolean {
    when {
      isLoading() -> {
        throw ValidationException.OperationInQueue
      }
      username.isNullOrEmpty() -> {
        throw ValidationException.UsernameEmpty
      }
      password.isNullOrEmpty() -> {
        throw ValidationException.PasswordEmpty
      }
    }
    return true
  }

  /**
   * BasicAuth
   * @param username of user
   * @param password of user
   */
  private fun basicAuth(
    username: String?,
    password: String?
  ) {

    sharedPrefUtils.userName = username

    val authRequestModel =
      AuthRequestModel(
        scopes = listOf("user", "repo", "gist", "notifications"),
        applicationId = APPLICATION_ID,
        clientId = CLIENT_ID,
        clientSecret = CLIENT_SECRET,
        callbackUrl = CALLBACK_URL
      )

    val credential =
      username?.let {
        password?.let { password ->
          Credentials.basic(
            username = username,
            password = password
          )
        }
      }

    credential?.let {
      viewModelScope.launch {
        when {
          networkManager.hasInternetAccess() -> {
            val basicTokenResponse = appRepository.getBasicToken(
              credential = credential,
              authRequestModel = authRequestModel
            )

            basicTokenResponse.first?.let { basicToken ->
              Timber.tag(TAG)
                .i("BasicToken: $basicToken")
              getUserInfo(basicToken)
              sharedPrefUtils.authToken = basicToken.token
            }
            basicTokenResponse.second?.let {
              _userLoggedInLiveData.value = false
              Timber.tag(TAG)
                .e("Exception: $it")
            }
          }
        }
      }
    }
  }

  private fun getUserInfo(basicToken: BasicToken) {
    viewModelScope.launch {
      when {
        networkManager.hasInternetAccess() -> {
          val userInfoResponse = appRepository.getUserInfo(
            authToken = TOKEN_PREFIX + basicToken.token
          )

          userInfoResponse.first?.let { user ->
            Timber.tag(TAG)
              .i("User: $user")
            insertUserInDb(user, basicToken.token)
          }
          userInfoResponse.second?.let {
            Timber.tag(TAG)
              .e("Exception: $it")
          }
        }
      }
    }
  }

  private fun insertUserInDb(
    user: User,
    authToken: String
  ) {
    viewModelScope.launch {
      appRepository.insertRoomUser(
        roomUser = user.toRoomUser(authToken)
      )
      _userLoggedInLiveData.value = true
    }
  }

  private fun isLoading(): Boolean = progressLiveData.value == true
}