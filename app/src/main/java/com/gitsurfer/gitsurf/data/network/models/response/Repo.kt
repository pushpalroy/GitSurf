package com.gitsurfer.gitsurf.data.network.models.response

import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomRepo
import com.squareup.moshi.Json

data class Repo(
  @Json(name = "id") val id: Int,
  @Json(name = "name") val name: String,
  @Json(name = "url") val url: String
)

fun Repo.toRoomRepo() = RoomRepo(
    id, name, url
)