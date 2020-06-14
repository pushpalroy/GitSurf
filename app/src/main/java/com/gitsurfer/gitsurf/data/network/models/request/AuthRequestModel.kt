package com.gitsurfer.gitsurf.data.network.models.request

import com.squareup.moshi.Json

data class AuthRequestModel(
  @Json(name = "scopes") val scopes: List<String>,
  @Json(name = "note") val applicationId: String,
  @Json(name = "noteUrl") val callbackUrl: String,
  @Json(name = "client_id") val clientId: String,
  @Json(name = "client_secret") val clientSecret: String
)