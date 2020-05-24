package com.gitsurfer.gitsurf.model.network.models.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BasicToken(
  @SerializedName("id") val id: Int,
  @SerializedName("url") val url: String,
  @SerializedName("token") val token: String,
  @SerializedName("created_at") val createdAt: Date,
  @SerializedName("updated_at") val createdUpdatedAt: Date,
  @SerializedName("scopes") val scopes: List<String>
)