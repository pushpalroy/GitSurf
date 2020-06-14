package com.gitsurfer.gitsurf.data.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Query
import com.gitsurfer.gitsurf.data.roomdatabase.base.BaseDAO
import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomUser

@Dao
abstract class UserDao : BaseDAO<RoomUser> {

  @Query("SELECT * FROM user_table WHERE login = :login limit 1")
  abstract suspend fun getUser(login: String): RoomUser?
}