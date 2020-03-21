package com.example.gitsurfer.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitsurfer.model.network.NetworkManager
import com.example.gitsurfer.model.usecases.exceptions.ValidationException
import com.example.gitsurfer.ui.base.BaseViewModel
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
    } catch (ex: ValidationException) {
      updateLiveDataException(ex)
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

  internal fun isLoading(): Boolean = progressLiveData.value == true
}