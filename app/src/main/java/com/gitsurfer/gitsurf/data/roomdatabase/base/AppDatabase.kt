package com.gitsurfer.gitsurf.data.roomdatabase.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gitsurfer.gitsurf.data.roomdatabase.base.AppDatabase.Companion.VERSION
import com.gitsurfer.gitsurf.data.roomdatabase.converter.RoomTypeConverters
import com.gitsurfer.gitsurf.data.roomdatabase.dao.FeedDao
import com.gitsurfer.gitsurf.data.roomdatabase.dao.UserDao
import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomFeed
import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomUser

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