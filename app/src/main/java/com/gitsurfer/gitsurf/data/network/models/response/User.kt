package com.gitsurfer.gitsurf.data.network.models.response

import com.gitsurfer.gitsurf.data.persistence.models.RoomUser
import com.squareup.moshi.Json
import java.util.Date

data class User(
  @Json(name = "login") val login: String,
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String?,
  @Json(name = "avatar_url") val avatarUrl: String?,
  @Json(name = "html_url") val profileUrl: String?,
  @Json(name = "company") val company: String?,
  @Json(name = "blog") val blog: String?,
  @Json(name = "location") val location: String?,
  @Json(name = "email") val email: String?,
  @Json(name = "bio") val bio: String?,
  @Json(name = "type") val type: String?,
  @Json(name = "public_repos") val publicRepos: Int?,
  @Json(name = "public_gists") val publicGists: Int?,
  @Json(name = "followers") val followers: Int?,
  @Json(name = "following") val following: Int?,
  @Json(name = "created_at") val createdAt: Date?,
  @Json(name = "updated_at") val updatedAt: Date?
)

fun User.toRoomUser() = RoomUser(
  authToken = "",
  login = login,
  name = name,
  avatarUrl = avatarUrl,
  profileUrl = profileUrl,
  company = company,
  blog = blog,
  location = location,
  email = email,
  bio = bio,
  type = type
)

fun User.toRoomUser(authToken: String) = RoomUser(
  authToken = authToken,
  login = login,
  name = name,
  avatarUrl = avatarUrl,
  profileUrl = profileUrl,
  company = company,
  blog = blog,
  location = location,
  email = email,
  bio = bio,
  type = type
)