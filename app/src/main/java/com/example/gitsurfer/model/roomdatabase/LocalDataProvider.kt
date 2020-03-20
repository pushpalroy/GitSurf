package com.example.gitsurfer.model.roomdatabase

import androidx.room.RoomDatabase
import com.example.gitsurfer.model.roomdatabase.base.AppRoomDatabase
import com.example.gitsurfer.model.roomdatabase.base.BaseDataProvider

class LocalDataProvider(
  private val appRoomDatabase: AppRoomDatabase
) : BaseDataProvider<RoomDatabase>() {

  override fun roomDatabase() = appRoomDatabase

  suspend fun getUser(accessToken: String) {
    appRoomDatabase.userDao()
        .getUser(accessToken)
  }
}