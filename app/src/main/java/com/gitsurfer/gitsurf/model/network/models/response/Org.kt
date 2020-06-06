package com.gitsurfer.gitsurf.model.network.models.response

import com.squareup.moshi.Json

data class Org(
  @Json(name = "id") val id: String,
  @Json(name = "login") val login: String,
  @Json(name = "display_login") val displayLogin: String?,
  @Json(name = "gravatar_id") val gravatarId: String,
  @Json(name = "url") val profileApiUrl: String,
  @Json(name = "avatar_url") val avatarUrl: String
)