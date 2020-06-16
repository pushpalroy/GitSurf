package com.gitsurfer.gitsurf.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.gitsurfer.gitsurf.data.persistence.base.BaseDAO
import com.gitsurfer.gitsurf.data.persistence.models.RoomUser

@Dao
abstract class UserDao : BaseDAO<RoomUser> {

  @Query("SELECT * FROM user_table WHERE login = :login limit 1")
  abstract suspend fun getUser(login: String): RoomUser?
}