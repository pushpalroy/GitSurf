package com.gitsurfer.gitsurf.injection.module

import androidx.lifecycle.ViewModel
import com.gitsurfer.gitsurf.injection.scope.ViewModelKey
import com.gitsurfer.gitsurf.ui.login.LoginViewModel
import com.gitsurfer.gitsurf.ui.main.MainViewModel
import com.gitsurfer.gitsurf.ui.main.bookmarks.BookmarksViewModel
import com.gitsurfer.gitsurf.ui.main.feed.FeedViewModel
import com.gitsurfer.gitsurf.ui.main.repo.RepoViewModel
import com.gitsurfer.gitsurf.ui.main.repo.readme.ReadmeViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ApplicationComponent::class)
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

  @Binds
  @IntoMap
  @ViewModelKey(BookmarksViewModel::class)
  abstract fun bindBookmarksViewModel(bookmarksViewModel: BookmarksViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RepoViewModel::class)
  abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(ReadmeViewModel::class)
  abstract fun bindReadmeViewModel(readmeViewModel: ReadmeViewModel): ViewModel
}