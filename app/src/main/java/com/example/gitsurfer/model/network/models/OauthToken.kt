package com.example.gitsurfer.model.network.models

import com.google.gson.annotations.SerializedName

data class OauthToken(
  @SerializedName("access_token") val accessToken: String,
  val scope: String
)