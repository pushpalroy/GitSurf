package com.gitsurfer.gitsurf.model.roomdatabase

import androidx.room.RoomDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.AppDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.BaseDataProvider

class LocalDataProvider(
  private val appDatabase: AppDatabase
) : BaseDataProvider<RoomDatabase>() {

  override fun roomDatabase() = appDatabase

  suspend fun getUser(accessToken: String) {
    appDatabase.userDao()
        .getUser(accessToken)
  }
}