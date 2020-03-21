package com.gitsurfer.gitsurf.injection.component

import com.gitsurfer.gitsurf.BaseApplication
import com.gitsurfer.gitsurf.injection.module.ActivityBindingModule
import com.gitsurfer.gitsurf.injection.module.AppModule
import com.gitsurfer.gitsurf.injection.module.RepositoryModule
import com.gitsurfer.gitsurf.injection.module.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      AppModule::class,
      ViewModelFactoryModule::class,
      AndroidSupportInjectionModule::class,
      ActivityBindingModule::class,
      RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
  @Component.Factory
  interface Factory : AndroidInjector.Factory<BaseApplication>
}