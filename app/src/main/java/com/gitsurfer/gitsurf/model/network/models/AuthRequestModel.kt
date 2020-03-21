package com.gitsurfer.gitsurf.model.network.models

import com.google.gson.annotations.SerializedName

data class AuthRequestModel(
  val scopes: List<String>,
  val note: String,
  val noteUrl: String,
  @SerializedName("client_id") val clientId: String,
  @SerializedName("client_secret") val clientSecret: String
)