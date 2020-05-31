package com.gitsurfer.gitsurf.model.roomdatabase

import androidx.room.RoomDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.AppDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.BaseDataProvider
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomUser

class LocalDataProvider(
  private val appDatabase: AppDatabase
) : BaseDataProvider<RoomDatabase>() {

  override fun roomDatabase() = appDatabase

  suspend fun insertUser(roomUser: RoomUser) {
    appDatabase.userDao()
        .insertUser(roomUser = roomUser)
  }

  suspend fun getUser(login: String): RoomUser? =
    appDatabase.userDao()
        .getUser(login = login)
}