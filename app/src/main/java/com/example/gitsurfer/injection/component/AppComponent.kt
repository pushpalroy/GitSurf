package com.example.gitsurfer.injection.component

import com.example.gitsurfer.BaseApplication
import com.example.gitsurfer.injection.module.ActivityBindingModule
import com.example.gitsurfer.injection.module.AppModule
import com.example.gitsurfer.injection.module.RepositoryModule
import com.example.gitsurfer.injection.module.ViewModelFactoryModule
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