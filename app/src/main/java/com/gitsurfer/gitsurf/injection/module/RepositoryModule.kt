package com.gitsurfer.gitsurf.injection.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.gitsurfer.gitsurf.BuildConfig
import com.gitsurfer.gitsurf.injection.qualifiers.ApplicationContext
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.NetworkDataProvider
import com.gitsurfer.gitsurf.model.network.api.LoginApi
import com.gitsurfer.gitsurf.model.network.api.UserApi
import com.gitsurfer.gitsurf.model.roomdatabase.LocalDataProvider
import com.gitsurfer.gitsurf.model.roomdatabase.base.AppDatabase
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.utils.BASE_URL
import com.gitsurfer.gitsurf.utils.ROOM_DATABASE_NAME
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

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

  @Provides
  @Singleton
  fun provideLoginApi(okHttpClient: OkHttpClient): LoginApi {
    return getRetrofit(okHttpClient).create(LoginApi::class.java)
  }

  @Provides
  @Singleton
  fun provideUserApi(okHttpClient: OkHttpClient): UserApi {
    return getRetrofit(okHttpClient).create(UserApi::class.java)
  }

  @Provides
  @Singleton
  fun provideAppDatabase(
    @ApplicationContext context: Context
  ): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        ROOM_DATABASE_NAME
    )
        .build()
  }

  @Provides
  @Singleton
  fun provideSharedPrefUtils(
    sharedPreferences: SharedPreferences
  ): SharedPrefUtils {
    return SharedPrefUtils(sharedPreferences)
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val httpBuilder = OkHttpClient.Builder()
    when {
      BuildConfig.DEBUG -> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpBuilder.interceptors()
            .add(httpLoggingInterceptor)
      }
    }
    return httpBuilder.build()
  }

  private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
  }
}