package com.gitsurfer.gitsurf.model.roomdatabase.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gitsurfer.gitsurf.model.roomdatabase.base.AppDatabase.Companion.VERSION
import com.gitsurfer.gitsurf.model.roomdatabase.converter.RoomTypeConverters
import com.gitsurfer.gitsurf.model.roomdatabase.dao.FeedDao
import com.gitsurfer.gitsurf.model.roomdatabase.dao.UserDao
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomFeed
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomUser

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