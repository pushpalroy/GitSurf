package com.gitsurfer.gitsurf.model.roomdatabase

import androidx.room.RoomDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.AppRoomDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.BaseDataProvider

class LocalDataProvider(
  private val appRoomDatabase: AppRoomDatabase
) : BaseDataProvider<RoomDatabase>() {

  override fun roomDatabase() = appRoomDatabase

  suspend fun getUser(accessToken: String) {
    appRoomDatabase.userDao()
        .getUser(accessToken)
  }
}