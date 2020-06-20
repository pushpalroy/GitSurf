package com.gitsurfer.gitsurf.data.network.models.response

import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
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