package com.gitsurfer.gitsurf.model.roomdatabase.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gitsurfer.gitsurf.model.roomdatabase.base.AppDatabase.Companion.VERSION
import com.gitsurfer.gitsurf.model.roomdatabase.dao.UserDao
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomUser

@Database(
    entities = [RoomUser::class],
    version = VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  companion object {
    const val VERSION = 1
  }

  abstract fun userDao(): UserDao
}