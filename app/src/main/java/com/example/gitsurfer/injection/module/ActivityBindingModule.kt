package com.example.gitsurfer.injection.module

import com.example.gitsurfer.injection.scope.ActivityScope
import com.example.gitsurfer.ui.login.LoginActivity
import com.example.gitsurfer.ui.login.LoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = [LoginActivityModule::class])
  internal abstract fun bindLoginActivity(): LoginActivity
}
