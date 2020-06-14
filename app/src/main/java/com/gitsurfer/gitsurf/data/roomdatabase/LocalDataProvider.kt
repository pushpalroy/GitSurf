package com.gitsurfer.gitsurf.data.roomdatabase

import androidx.room.RoomDatabase
import com.gitsurfer.gitsurf.data.roomdatabase.base.AppDatabase
import com.gitsurfer.gitsurf.data.roomdatabase.base.BaseDataProvider
import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomFeed
import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomUser
import com.gitsurfer.gitsurf.utils.withIOContext

class LocalDataProvider(
  private val appDatabase: AppDatabase
) : BaseDataProvider<RoomDatabase>() {

  override fun roomDatabase() = appDatabase

  suspend fun insertUser(roomUser: RoomUser) = withIOContext {
    appDatabase.userDao()
        .insert(obj = roomUser)
  }

  suspend fun getUser(login: String): RoomUser? =
    appDatabase.userDao()
        .getUser(login = login)

  suspend fun insertFeed(roomFeed: RoomFeed) = withIOContext {
    appDatabase.feedDao()
        .insert(obj = roomFeed)
  }

  fun getAllFeeds() =
    appDatabase.feedDao()
        .getAllFeeds()

  suspend fun deleteFeed(roomFeed: RoomFeed) = withIOContext {
    appDatabase.feedDao()
        .delete(roomFeed)
  }
}