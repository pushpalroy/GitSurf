package com.gitsurfer.gitsurf.model.network.models.response

import com.google.gson.annotations.SerializedName

data class OauthToken(
  @SerializedName("access_token") val accessToken: String,
  @SerializedName("scope") val scope: String
)