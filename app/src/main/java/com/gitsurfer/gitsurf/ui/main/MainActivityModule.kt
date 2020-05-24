package com.gitsurfer.gitsurf.ui.main

import com.gitsurfer.gitsurf.injection.scope.FragmentScope
import com.gitsurfer.gitsurf.ui.main.feed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun bindFeedFragment(): FeedFragment
}