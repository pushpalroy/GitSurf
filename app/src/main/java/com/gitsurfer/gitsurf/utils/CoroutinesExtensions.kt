package com.gitsurfer.gitsurf.utils

import com.gitsurfer.gitsurf.model.network.models.ResponseUnauthorized
import com.gitsurfer.gitsurf.utils.exceptions.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.UnknownHostException

suspend fun <T> withIOContext(function: suspend () -> T): Pair<T?, Exception?> {
  return withContext(Dispatchers.IO) {
    try {
      Pair(function.invoke(), null)
    } catch (ex: Exception) {
      Timber.e(ex)
      Pair(null, ex)
    }
  }
}

suspend fun <T> networkCall(function: suspend () -> Response<T>): Pair<T?, Exception?> {
  return withContext(Dispatchers.IO) {
    try {
      val response = function.invoke()
      when {
        response.isSuccessful -> Pair(response.body(), null)
        else -> handleFailure(response)
      }
    } catch (ex: Exception) {
      Timber.e(ex)
      when (ex) {
        is UnknownHostException -> Pair(
            null,
            NoInternetException()
        )
        else -> Pair(null, ex)
      }
    }
  }
}

private fun <T> handleFailure(response: Response<T>): Pair<Nothing?, Exception> {
  return when {
    response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> {
      Pair(null, UnauthorizedException())
    }
    response.code() == HttpURLConnection.HTTP_FORBIDDEN -> {
      Pair(null, ForbiddenException(false))
    }
    response.code() != HttpURLConnection.HTTP_OK -> {
      Pair(null, handleApiError(response))
    }
    else -> Pair(
        null,
        NetworkException(response.errorBody())
    )
  }
}

private fun <T> handleApiError(response: Response<T>): Exception {
  return try {
    val responseError =
      response.errorBody()
          ?.string()
          ?.toKotlinObject<ResponseUnauthorized>()
    responseError?.let {
      HttpNotSuccessException(
          it
      )
    }
        ?: NetworkException(
            response.errorBody()
        )
  } catch (ex: Exception) {
    HttpNotSuccessException(
        ResponseUnauthorized(
            "Error",
            "Server error occurred",
            response.code()
        )
    )
  }
}

inline fun <reified T> String.toKotlinObject(): T? {
  val jsonAdapter = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
      .adapter(T::class.java)
  return jsonAdapter.fromJson(this)
}