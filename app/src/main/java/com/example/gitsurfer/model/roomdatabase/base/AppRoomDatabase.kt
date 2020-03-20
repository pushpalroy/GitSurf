package com.example.gitsurfer.model.roomdatabase.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitsurfer.model.roomdatabase.base.AppRoomDatabase.Companion.VERSION
import com.example.gitsurfer.model.roomdatabase.dao.UserDao
import com.example.gitsurfer.model.roomdatabase.models.RoomUser

@Database(
    entities = [RoomUser::class],
    version = VERSION,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
  companion object {
    const val VERSION = 1
  }

  abstract fun userDao(): UserDao
}