package com.gitsurfer.gitsurf.data.roomdatabase.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDAO<T> {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(obj: T): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMultiple(objList: List<T>): List<Long>

  @Delete
  suspend fun delete(obj: T)

  @Delete
  suspend fun delete(objects: List<T>)
}