package com.gitsurfer.gitsurf.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gitsurfer.gitsurf.BuildConfig
import com.gitsurfer.gitsurf.model.network.NetworkManager
import com.gitsurfer.gitsurf.model.network.models.AuthRequestModel
import com.gitsurfer.gitsurf.model.usecases.exceptions.ValidationException
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(val networkManager: NetworkManager) : BaseViewModel() {

  private val _userLoggedInLiveData = MutableLiveData<Boolean>()
  val userLoggedInLiveData: LiveData<Boolean>
    get() = _userLoggedInLiveData

  var username: String? = null
  var password: String? = null

  fun loginUser() {
    try {
      isValidForm()
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
   *
   */
  private fun basicLogin(
    username: String,
    password: String
  ) {
    var authRequestModel = AuthRequestModel(
        scopes = listOf("user", "repo", "gist", "notifications"),
        note = BuildConfig.APPLICATION_ID,
        clientId = "",
        clientSecret = "",
        noteUrl = ""
    )
  }

  internal fun isLoading(): Boolean = progressLiveData.value == true
}