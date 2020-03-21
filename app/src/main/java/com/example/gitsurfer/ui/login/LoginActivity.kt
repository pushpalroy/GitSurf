package com.example.gitsurfer.ui.login

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.gitsurfer.R
import com.example.gitsurfer.databinding.ActivityLoginBinding
import com.example.gitsurfer.model.usecases.exceptions.ValidationException
import com.example.gitsurfer.model.usecases.exceptions.ValidationException.*
import com.example.gitsurfer.ui.base.BaseActivity
import com.example.gitsurfer.utils.clearErrorOnTextChange
import com.example.gitsurfer.utils.exceptions.NoInternetException
import com.example.gitsurfer.utils.hideKeyboard
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

  override fun getViewModelClass() = LoginViewModel::class.java
  override fun getLayoutId() = R.layout.activity_login

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initView()
    listenToLiveData()
  }

  private fun initView() {
    bindProgressButton(binding.loginBtn)
    binding.loginBtn.attachTextChangeAnimator()
    binding.userNameEt.clearErrorOnTextChange(binding.userNameLayout)
    binding.passwordEt.clearErrorOnTextChange(binding.passwordLayout)

    binding.loginBtn.setOnClickListener {
      it?.context?.hideKeyboard()
      viewModel.loginUser()
    }
  }

  private fun listenToLiveData() {
    viewModel.progressLiveData.observeSingleLiveData(this, Observer {
      if (it == true) {
        binding.loginBtn.isEnabled = false
        binding.loginBtn.showProgress {
          buttonTextRes = R.string.loading
          progressColor = Color.WHITE
        }
      } else {
        binding.loginBtn.isEnabled = true
        binding.loginBtn.hideProgress(R.string.loading)
      }
    })

    viewModel.exceptionLiveData.observe(this, Observer {
      it?.let {
        when (it) {
          is NoInternetException ->
            showSnackBarMessage(R.string.msg_no_internet)

          is ValidationException -> when (it) {
            is UsernameEmpty -> {
              showSnackBarMessage(R.string.msg_username_empty)
            }
            is PasswordEmpty -> {
              showSnackBarMessage(R.string.msg_password_empty)
            }
            is OperationInQueue -> {
              // No Action
            }
            else -> {
              // No Action
            }
          }
          else -> {
            showSnackBarMessage(R.string.msg_unknown_error)
          }
        }
      }
    })
  }
}
