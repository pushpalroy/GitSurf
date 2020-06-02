package com.gitsurfer.gitsurf.model.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Query
import com.gitsurfer.gitsurf.model.roomdatabase.base.BaseDAO
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomUser

@Dao
abstract class UserDao : BaseDAO<RoomUser> {

  @Query("SELECT * FROM user_table WHERE login = :login limit 1")
  abstract suspend fun getUser(login: String): RoomUser?
}