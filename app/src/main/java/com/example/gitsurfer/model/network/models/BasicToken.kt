package com.example.gitsurfer.model.network.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BasicToken(
  val id: Int,
  val url: String,
  val token: String,
  @SerializedName("created_at") val createdAt: Date,
  @SerializedName("updated_at") val createdUpdatedAt: Date,
  val scopes: List<String>
)