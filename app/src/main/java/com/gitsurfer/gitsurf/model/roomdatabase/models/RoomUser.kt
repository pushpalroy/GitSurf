package com.gitsurfer.gitsurf.model.roomdatabase.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomUser.Companion.ROOM_USER
import kotlinx.android.parcel.Parcelize

@Entity(tableName = ROOM_USER)
@Parcelize
data class RoomUser(
  @PrimaryKey(autoGenerate = false)
  val login: String,
  val authToken: String,
  val name: String,
  val avatarUrl: String,
  val profileUrl: String,
  val company: String,
  val blog: String,
  val location: String,
  val email: String,
  val bio: String,
  val type: String
) : Parcelable {

  companion object {
    const val ROOM_USER = "user_table"
  }
}