package com.example.gitsurfer.injection.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.gitsurfer.BuildConfig
import com.example.gitsurfer.model.AppRepository
import com.example.gitsurfer.model.network.NetworkDataProvider
import com.example.gitsurfer.model.network.api.RetrofitApi
import com.example.gitsurfer.model.roomdatabase.LocalDataProvider
import com.example.gitsurfer.model.roomdatabase.base.AppRoomDatabase
import com.example.gitsurfer.model.utils.SharedPrefUtils
import com.example.gitsurfer.utils.BASE_URL
import com.example.gitsurfer.utils.ROOM_DATABASE_NAME
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
  ) = AppRepository(networkDataProvider, localDataProvider)

  @Provides
  @Singleton
  fun provideNetworkDataProvider(
    retrofitApi: RetrofitApi
  ) = NetworkDataProvider(retrofitApi)

  @Provides
  @Singleton
  fun provideLocalDataProvider(
    context: Context
  ): AppRoomDatabase {
    return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
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

  @Provides
  @Singleton
  fun provideRestAdapter(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
  }
}