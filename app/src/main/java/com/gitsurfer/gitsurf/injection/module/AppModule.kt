package com.gitsurfer.gitsurf.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gitsurfer.gitsurf.injection.factory.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
    return ViewModelFactory(creators = creators)
  }
}