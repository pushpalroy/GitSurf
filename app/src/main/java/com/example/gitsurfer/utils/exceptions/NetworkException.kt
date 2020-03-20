package com.example.gitsurfer.utils.exceptions

import okhttp3.ResponseBody

class NetworkException(private val errorBody: ResponseBody?) : Exception() {
  override val message: String?
    get() = errorBody?.string() ?: errorBody.toString()
}