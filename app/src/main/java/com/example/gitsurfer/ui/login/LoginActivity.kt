package com.example.gitsurfer.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.gitsurfer.R
import com.example.gitsurfer.databinding.ActivityLoginBinding
import com.example.gitsurfer.ui.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

  override fun getViewModelClass() = LoginViewModel::class.java
  override fun getLayoutId() = R.layout.activity_login

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    listenToLiveData()
  }

  private fun listenToLiveData() {
    viewModel.progressLiveData.observeSingleLiveData(this, Observer {

    })
  }
}
