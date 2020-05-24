package com.gitsurfer.gitsurf.injection.module

import androidx.lifecycle.ViewModel
import com.gitsurfer.gitsurf.injection.scope.ViewModelKey
import com.gitsurfer.gitsurf.ui.login.LoginViewModel
import com.gitsurfer.gitsurf.ui.main.MainViewModel
import com.gitsurfer.gitsurf.ui.main.feed.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(FeedViewModel::class)
  abstract fun bindFeedViewModel(feedViewModel: FeedViewModel): ViewModel
}