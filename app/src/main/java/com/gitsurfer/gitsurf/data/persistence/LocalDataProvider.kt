package com.gitsurfer.gitsurf.data.persistence

import androidx.room.RoomDatabase
import com.gitsurfer.gitsurf.data.persistence.database.AppDatabase
import com.gitsurfer.gitsurf.data.persistence.base.BaseDataProvider
import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.data.persistence.models.RoomUser
import com.gitsurfer.gitsurf.data.utils.withIOContext

class LocalDataProvider(
  private val appDatabase: AppDatabase
) : BaseDataProvider<RoomDatabase>() {

  override fun roomDatabase() = appDatabase

  suspend fun insertUser(roomUser: RoomUser) =
    withIOContext {
      appDatabase.userDao()
        .insert(obj = roomUser)
    }

  suspend fun getUser(login: String): RoomUser? =
    appDatabase.userDao()
        .getUser(login = login)

  suspend fun insertFeed(roomFeed: RoomFeed) =
    withIOContext {
      appDatabase.feedDao()
        .insert(obj = roomFeed)
    }

  fun getAllFeeds() =
    appDatabase.feedDao()
        .getAllFeeds()

  suspend fun deleteFeed(roomFeed: RoomFeed) =
    withIOContext {
      appDatabase.feedDao()
        .delete(roomFeed)
    }
}