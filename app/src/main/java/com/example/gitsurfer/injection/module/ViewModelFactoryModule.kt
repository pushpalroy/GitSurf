package com.example.gitsurfer.injection.module

import androidx.lifecycle.ViewModel
import com.example.gitsurfer.injection.scope.ViewModelKey
import com.example.gitsurfer.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}