package com.gitsurfer.gitsurf.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.NetworkManager
import com.gitsurfer.gitsurf.model.network.models.request.AuthRequestModel
import com.gitsurfer.gitsurf.model.network.models.response.BasicToken
import com.gitsurfer.gitsurf.model.usecases.exceptions.ValidationException
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.utils.APPLICATION_ID
import com.gitsurfer.gitsurf.utils.CALLBACK_URL
import com.gitsurfer.gitsurf.utils.CLIENT_ID
import com.gitsurfer.gitsurf.utils.CLIENT_SECRET
import kotlinx.coroutines.launch
import okhttp3.Credentials
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
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

  var username: String? = ""
  var password: String? = ""

  fun loginUser() {
    try {
      isValidForm()
      basicAuth(username, password)
    } catch (exception: ValidationException) {
      updateLiveDataException(exception)
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
              _userLoggedInLiveData.value = true
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
              authToken = "token ${basicToken.token}"
          )

          userInfoResponse.first?.let {
            Timber.tag(TAG)
                .i("UserBio: ${it.bio}")
          }
          userInfoResponse.second?.let {
            Timber.tag(TAG)
                .e("Exception: $it")
          }
        }
      }
    }
  }

  private fun isLoading(): Boolean = progressLiveData.value == true
}