package com.gitsurfer.gitsurf.model.network.models.request

import com.google.gson.annotations.SerializedName

data class AuthRequestModel(
  @SerializedName("scopes") val scopes: List<String>,
  @SerializedName("note") val applicationId: String,
  @SerializedName("noteUrl") val callbackUrl: String,
  @SerializedName("client_id") val clientId: String,
  @SerializedName("client_secret") val clientSecret: String
)