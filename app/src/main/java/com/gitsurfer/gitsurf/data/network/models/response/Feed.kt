package com.gitsurfer.gitsurf.data.network.models.response

import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.data.persistence.models.RoomPayload
import com.squareup.moshi.Json
import java.util.Date

data class Feed(
  @Json(name = "id") val id: String,
  @Json(name = "type") val type: String,
  @Json(name = "actor") val actor: Actor,
  @Json(name = "repo") val repo: Repo,
  @Json(name = "payload") val payload: Payload?,
  @Json(name = "public") val public: Boolean,
  @Json(name = "created_at") val createdAt: Date,
  @Json(name = "org") val org: Org?
)

fun Feed.toRoomFeed() = RoomFeed(
  id = id,
  type = type,
  actor = actor.toRoomActor(),
  repo = repo.toRoomRepo(),
  payload = payload?.toRoomPayload(),
  createdAt = createdAt
)

data class Payload(
  @Json(name = "action") val action: String?,
  @Json(name = "member") val member: Member?
)

fun Payload.toRoomPayload() = RoomPayload(
  action = action,
  member = member?.toRoomMember()
)

data class Org(
  @Json(name = "id") val id: String,
  @Json(name = "login") val login: String,
  @Json(name = "display_login") val displayLogin: String?,
  @Json(name = "gravatar_id") val gravatarId: String,
  @Json(name = "url") val profileApiUrl: String,
  @Json(name = "avatar_url") val avatarUrl: String
)