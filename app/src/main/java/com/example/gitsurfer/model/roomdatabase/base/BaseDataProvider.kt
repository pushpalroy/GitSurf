package com.example.gitsurfer.model.roomdatabase.base

import androidx.room.RoomDatabase
import timber.log.Timber

/*
* BaseDataProvider tells that RoomDatabase will be used and provides
* an implementation of the closeDatabase() function.
*/
abstract class BaseDataProvider<T : RoomDatabase> : BaseDatabase<T>() {
  override fun closeDatabase() {
    try {
      roomDatabase().close()
      Timber.d("Database closed when log out")
    } catch (ex: Exception) {
      Timber.e(ex)
    }
  }
}