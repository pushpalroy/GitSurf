package com.gitsurfer.gitsurf.injection.module

import com.gitsurfer.gitsurf.injection.scope.ActivityScope
import com.gitsurfer.gitsurf.ui.login.LoginActivity
import com.gitsurfer.gitsurf.ui.login.LoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = [LoginActivityModule::class])
  internal abstract fun bindLoginActivity(): LoginActivity
}
