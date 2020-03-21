package com.gitsurfer.gitsurf.model.roomdatabase.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomUser.Companion.ROOM_USER
import kotlinx.android.parcel.Parcelize

@Entity(tableName = ROOM_USER)
@Parcelize
data class RoomUser(
  @PrimaryKey val accessToken: String,
  val authTime: Long,
  val expireIn: Int,
  val scope: String,
  val selected: Boolean,
  val loginId: String,
  val name: String,
  val avatar: String
) : Parcelable {

  companion object {
    const val ROOM_USER = "user_table"
  }

}