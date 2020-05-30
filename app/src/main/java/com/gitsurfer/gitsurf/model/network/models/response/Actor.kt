package com.gitsurfer.gitsurf.model.network.models.response

import com.google.gson.annotations.SerializedName

data class Actor(
  @SerializedName("id") val id: String,
  @SerializedName("login") val login: String,
  @SerializedName("display_login") val displayLogin: String,
  @SerializedName("gravatar_id") val gravatarId: String,
  @SerializedName("url") val profileApiUrl: String,
  @SerializedName("avatar_url") val avatarUrl: String
)