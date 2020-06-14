package com.gitsurfer.gitsurf.data.network.models

data class ResponseUnauthorized(
  val error: String,
  val message: String,
  val statusCode: Int
)