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
import com.gitsurfer.gitsurf.ui.base.BaseActivity
import com.gitsurfer.gitsurf.ui.main.MainActivity
import com.gitsurfer.gitsurf.utils.exceptions.NoInternetException
import com.gitsurfer.gitsurf.utils.exceptions.ValidationException
import com.gitsurfer.gitsurf.utils.exceptions.ValidationException.PasswordEmpty
import com.gitsurfer.gitsurf.utils.exceptions.ValidationException.UsernameEmpty
import com.gitsurfer.gitsurf.utils.ui.AppNavigator
import com.gitsurfer.gitsurf.utils.ui.clearErrorOnTextChange
import com.gitsurfer.gitsurf.utils.ui.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

  override val viewModel: LoginViewModel by viewModels()
  override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initView()
    listenToLiveData()
  }

  private fun initView() {
    setContentView(binding.root)
    binding.vm = viewModel
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
    viewModel.progressLiveData.observe(this, Observer {
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
            showSnackBar(R.string.msg_no_internet, binding.root)

          is ValidationException -> when (it) {
            is UsernameEmpty -> {
              showSnackBar(R.string.msg_username_empty, binding.root)
            }
            is PasswordEmpty -> {
              showSnackBar(R.string.msg_password_empty, binding.root)
            }
          }
          else -> {
            showSnackBar(R.string.msg_unknown_error, binding.root)
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
