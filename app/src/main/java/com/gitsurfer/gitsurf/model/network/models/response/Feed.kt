package com.gitsurfer.gitsurf.model.network.models.response

import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomFeed
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Feed(
  @SerializedName("id") val id: String,
  @SerializedName("type") val type: String,
  @SerializedName("actor") val actor: Actor,
  @SerializedName("repo") val repo: Repo,
  @SerializedName("payload") val payload: Payload?,
  @SerializedName("public") val public: Boolean,
  @SerializedName("created_at") val createdAt: Date,
  @SerializedName("org") val org: Org
)

fun Feed.toRoomFeed() = RoomFeed(
    id = id,
    type = type,
    actor = actor.toRoomActor(),
    repo = repo.toRoomRepo(),
    payload = payload?.toRoomPayload(),
    createdAt = createdAt
)