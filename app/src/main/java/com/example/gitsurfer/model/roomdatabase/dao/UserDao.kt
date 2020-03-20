package com.example.gitsurfer.model.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.gitsurfer.model.roomdatabase.base.BaseDAO
import com.example.gitsurfer.model.roomdatabase.models.RoomUser

@Dao
abstract class UserDao : BaseDAO<RoomUser> {

  @Query("SELECT * FROM user_table WHERE accessToken = :accessToken limit 1")
  abstract suspend fun getUser(accessToken: String): RoomUser?

}