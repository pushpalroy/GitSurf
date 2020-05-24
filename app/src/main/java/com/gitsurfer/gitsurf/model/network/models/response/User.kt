package com.gitsurfer.gitsurf.model.network.models.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class User(
  @SerializedName("login") val login: String,
  @SerializedName("id") val id: String,
  @SerializedName("name") val name: String,
  @SerializedName("avatar_url") val avatarUrl: String,
  @SerializedName("html_url") val profileUrl: String,
  @SerializedName("company") val company: String,
  @SerializedName("blog") val blog: String,
  @SerializedName("location") val location: String,
  @SerializedName("email") val email: String,
  @SerializedName("bio") val bio: String,
  @SerializedName("type") val type: String,
  @SerializedName("public_repos") val publicRepos: Int,
  @SerializedName("public_gists") val publicGists: Int,
  @SerializedName("followers") val followers: Int,
  @SerializedName("following") val following: Int,
  @SerializedName("createdAt") val createdAt: Date,
  @SerializedName("updatedAt") val updatedAt: Date
)