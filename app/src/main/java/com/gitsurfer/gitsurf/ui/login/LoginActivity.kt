package com.gitsurfer.gitsurf.ui.login

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.ActivityLoginBinding
import com.gitsurfer.gitsurf.model.usecases.exceptions.ValidationException
import com.gitsurfer.gitsurf.model.usecases.exceptions.ValidationException.PasswordEmpty
import com.gitsurfer.gitsurf.model.usecases.exceptions.ValidationException.UsernameEmpty
import com.gitsurfer.gitsurf.ui.base.AppNavigator
import com.gitsurfer.gitsurf.ui.base.BaseActivity
import com.gitsurfer.gitsurf.ui.main.MainActivity
import com.gitsurfer.gitsurf.utils.clearErrorOnTextChange
import com.gitsurfer.gitsurf.utils.exceptions.NoInternetException
import com.gitsurfer.gitsurf.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

  override fun getLayoutId() = R.layout.activity_login
  private val viewModel: LoginViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initView()
    listenToLiveData()
  }

  private fun initView() {
    binding.viewModel = viewModel
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
          }
          else -> {
            showSnackBarMessage(R.string.msg_unknown_error)
          }
        }
      }
    })

    viewModel.userLoggedInLiveData.observe(this, Observer { isLoggedIn ->
      run {
        if (isLoggedIn) {
          AppNavigator.startActivity(MainActivity::class.java, this, clearBackStack = true)
        }
      }
    })
  }
}
