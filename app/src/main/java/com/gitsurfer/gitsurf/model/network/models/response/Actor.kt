package com.gitsurfer.gitsurf.model.network.models.response

import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomActor
import com.squareup.moshi.Json

data class Actor(
    @Json(name = "id") val id: String,
    @Json(name = "login") val login: String,
    @Json(name = "display_login") val displayLogin: String?,
    @Json(name = "gravatar_id") val gravatarId: String,
    @Json(name = "url") val profileApiUrl: String,
    @Json(name = "avatar_url") val avatarUrl: String
)

fun Actor.toRoomActor() = RoomActor(
    id, login, displayLogin, gravatarId, profileApiUrl, avatarUrl
)