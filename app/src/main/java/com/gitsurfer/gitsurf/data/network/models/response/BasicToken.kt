package com.gitsurfer.gitsurf.data.network.models.response

import com.squareup.moshi.Json
import java.util.Date

data class BasicToken(
  @Json(name = "id") val id: Int,
  @Json(name = "url") val url: String,
  @Json(name = "token") val token: String,
  @Json(name = "token_last_eight") val tokenLastEightDigit: String,
  @Json(name = "hashed_token") val hashedToken: String,
  @Json(name = "created_at") val createdAt: Date,
  @Json(name = "updated_at") val createdUpdatedAt: Date,
  @Json(name = "scopes") val scopes: List<String>
)