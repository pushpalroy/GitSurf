package com.gitsurfer.gitsurf.data.network.models.response

import com.gitsurfer.gitsurf.data.persistence.models.RoomRepo
import com.squareup.moshi.Json

data class Repo(
  @Json(name = "id") val id: Int,
  @Json(name = "name") val name: String,
  @Json(name = "full_name") val fullName: String?,
  @Json(name = "owner") val owner: User?,
  @Json(name = "html_url") val htmlUrl: String?,
  @Json(name = "description") val description: String?,
  @Json(name = "url") val url: String,
  @Json(name = "default_branch") val defaultBranch: String?,
  @Json(name = "language") val language: String?
)

fun Repo.toRoomRepo() = RoomRepo(
  id, name, url
)