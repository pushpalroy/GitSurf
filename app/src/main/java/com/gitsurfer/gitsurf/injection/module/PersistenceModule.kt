package com.gitsurfer.gitsurf.injection.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.gitsurfer.gitsurf.data.persistence.database.AppDatabase
import com.gitsurfer.gitsurf.data.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.utils.constants.AUTH_DATA
import com.gitsurfer.gitsurf.utils.constants.ROOM_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PersistenceModule {

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
  fun provideSharePrefs(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences(AUTH_DATA, Context.MODE_PRIVATE)
  }
}