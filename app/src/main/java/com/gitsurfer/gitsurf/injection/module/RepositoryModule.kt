package com.gitsurfer.gitsurf.injection.module

import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkDataProvider
import com.gitsurfer.gitsurf.data.network.api.LoginApi
import com.gitsurfer.gitsurf.data.network.api.UserApi
import com.gitsurfer.gitsurf.data.persistence.LocalDataProvider
import com.gitsurfer.gitsurf.data.persistence.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

  @Provides
  @Singleton
  fun provideAppRepository(
    networkDataProvider: NetworkDataProvider,
    localDataProvider: LocalDataProvider
  ) = AppRepository(
    networkDataProvider = networkDataProvider,
    localDataProvider = localDataProvider
  )

  @Provides
  @Singleton
  fun provideNetworkDataProvider(
    loginApi: LoginApi,
    userApi: UserApi
  ) = NetworkDataProvider(
    loginApi = loginApi,
    userApi = userApi
  )

  @Provides
  @Singleton
  fun provideLocalDataProvider(
    database: AppDatabase
  ): LocalDataProvider {
    return LocalDataProvider(
      appDatabase = database
    )
  }
}