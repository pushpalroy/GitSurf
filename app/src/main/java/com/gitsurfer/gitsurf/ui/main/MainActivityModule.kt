package com.gitsurfer.gitsurf.ui.main

import com.gitsurfer.gitsurf.injection.scope.FragmentScope
import com.gitsurfer.gitsurf.ui.main.feed.FeedFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment

@Module
abstract class MainActivityModule {

  @FragmentScope
  @ContributesAndroidInjector(modules = [(FeedFragmentModule::class)])
  abstract fun bindFeedFragment(): FeedFragment

  @Module
  internal abstract class FeedFragmentModule : FragmentModule<FeedFragment>()

  @Module(includes = [BaseFragmentModule::class])
  abstract class FragmentModule<in T : DaggerFragment> {
    @Binds
    @FragmentScope
    internal abstract fun bindFragment(fragment: T): DaggerFragment
  }

  /**
   * Fragment specific common dependencies should be placed here
   */
  @Module
  open class BaseFragmentModule
}