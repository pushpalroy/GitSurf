package com.gitsurfer.gitsurf.data.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gitsurfer.gitsurf.data.persistence.database.AppDatabase.Companion.VERSION
import com.gitsurfer.gitsurf.data.persistence.converter.RoomTypeConverters
import com.gitsurfer.gitsurf.data.persistence.dao.FeedDao
import com.gitsurfer.gitsurf.data.persistence.dao.UserDao
import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.data.persistence.models.RoomUser

@Database(
    entities = [
      RoomUser::class,
      RoomFeed::class],
    version = VERSION,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
  companion object {
    const val VERSION = 1
  }

  abstract fun userDao(): UserDao
  abstract fun feedDao(): FeedDao
}