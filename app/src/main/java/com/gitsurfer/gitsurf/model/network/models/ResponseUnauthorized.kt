package com.gitsurfer.gitsurf.model.network.models

data class ResponseUnauthorized(
  val error: String,
  val message: String,
  val statusCode: Int
)