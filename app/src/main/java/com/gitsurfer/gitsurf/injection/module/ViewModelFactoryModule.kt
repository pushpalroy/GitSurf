package com.gitsurfer.gitsurf.injection.module

import androidx.lifecycle.ViewModel
import com.gitsurfer.gitsurf.injection.scope.ViewModelKey
import com.gitsurfer.gitsurf.ui.login.LoginViewModel
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