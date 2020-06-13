package com.gitsurfer.gitsurf.injection.module

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gitsurfer.gitsurf.injection.factory.ViewModelFactory
import com.gitsurfer.gitsurf.model.network.NetworkManager
import com.gitsurfer.gitsurf.utils.AUTH_DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun provideViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
    return ViewModelFactory(creators = creators)
  }

  @Provides
  @Singleton
  fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
    return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  @Provides
  @Singleton
  fun provideNetworkManager(connectivityManager: ConnectivityManager): NetworkManager {
    return NetworkManager(connectivityManager = connectivityManager)
  }

  @Provides
  @Singleton
  fun provideSharePrefs(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE)
  }
}