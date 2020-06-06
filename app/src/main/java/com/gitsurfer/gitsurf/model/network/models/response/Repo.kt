package com.gitsurfer.gitsurf.model.network.models.response

import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomRepo
import com.squareup.moshi.Json

data class Repo(
  @Json(name = "id") val id: Int,
  @Json(name = "name") val name: String,
  @Json(name = "url") val url: String
)

fun Repo.toRoomRepo() = RoomRepo(
    id, name, url
)