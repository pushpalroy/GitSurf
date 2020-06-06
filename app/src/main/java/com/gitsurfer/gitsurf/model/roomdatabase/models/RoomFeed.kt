package com.gitsurfer.gitsurf.model.roomdatabase.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomFeed.Companion.ROOM_FEED
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Entity(tableName = ROOM_FEED)
@Parcelize
data class RoomFeed(
  @PrimaryKey(autoGenerate = false)
  val id: String,
  val type: String,
  val actor: RoomActor?,
  val repo: RoomRepo?,
  val payload: RoomPayload?,
  val createdAt: Date?
) : Parcelable {
  companion object {
    const val ROOM_FEED = "feed_table"
  }
}

@Parcelize
data class RoomActor(
  val id: String,
  val login: String,
  val displayLogin: String?,
  val gravatarId: String,
  val profileApiUrl: String,
  val avatarUrl: String
) : Parcelable

@Parcelize
data class RoomRepo(
  val id: Int,
  val name: String,
  val url: String
) : Parcelable

@Parcelize
data class RoomPayload(
  val action: String?,
  val member: RoomMember?
) : Parcelable

@Parcelize
data class RoomMember(
  val login: String,
  val id: String,
  val avatarUrl: String,
  val gravatarId: String,
  val profileApiUrl: String,
  val htmlUrl: String,
  val type: String,
  val siteAdmin: Boolean
) : Parcelable