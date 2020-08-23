package com.gitsurfer.gitsurf.data.network.models.response

import android.os.Parcelable
import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.data.persistence.models.RoomPayload
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Feed(
  @Json(name = "id") val id: String,
  @Json(name = "type") val type: String,
  @Json(name = "actor") val actor: Actor,
  @Json(name = "repo") val repo: Repo,
  @Json(name = "payload") val payload: Payload?,
  @Json(name = "public") val public: Boolean,
  @Json(name = "created_at") val createdAt: Date,
  @Json(name = "org") val org: Org?
) : Parcelable

fun Feed.toRoomFeed() = RoomFeed(
  id = id,
  type = type,
  actor = actor.toRoomActor(),
  repo = repo.toRoomRepo(),
  payload = payload?.toRoomPayload(),
  createdAt = createdAt
)

@Parcelize
data class Payload(
  @Json(name = "action") val action: String?,
  @Json(name = "member") val member: Member?
) : Parcelable

fun Payload.toRoomPayload() = RoomPayload(
  action = action,
  member = member?.toRoomMember()
)

@Parcelize
data class Org(
  @Json(name = "id") val id: String,
  @Json(name = "login") val login: String,
  @Json(name = "display_login") val displayLogin: String?,
  @Json(name = "gravatar_id") val gravatarId: String,
  @Json(name = "url") val profileApiUrl: String,
  @Json(name = "avatar_url") val avatarUrl: String
) : Parcelable