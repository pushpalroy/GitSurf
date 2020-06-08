package com.gitsurfer.gitsurf.ui.main

import com.gitsurfer.gitsurf.injection.scope.FragmentScope
import com.gitsurfer.gitsurf.ui.main.bookmarks.BookmarksFragment
import com.gitsurfer.gitsurf.ui.main.feed.FeedFragment
import com.gitsurfer.gitsurf.ui.main.repo.RepoFragment
import com.gitsurfer.gitsurf.ui.main.repo.readme.ReadmeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun bindFeedFragment(): FeedFragment

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun bindBookmarksFragment(): BookmarksFragment

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun bindRepoFragment(): RepoFragment

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun bindReadmeFragment(): ReadmeFragment
}