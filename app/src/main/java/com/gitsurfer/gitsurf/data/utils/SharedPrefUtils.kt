package com.gitsurfer.gitsurf.data.utils

import android.content.SharedPreferences

const val KEY_USER_NAME = "KEY_USER_NAME"
const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"

class SharedPrefUtils(private val sharedPreferences: SharedPreferences) {

  var userName: String?
    get() {
      return sharedPreferences.getString(KEY_USER_NAME, null)
    }
    set(value) {
      sharedPreferences.edit()
          .putString(KEY_USER_NAME, value)
          .apply()
    }

  var authToken: String?
    get() {
      return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }
    set(value) {
      sharedPreferences.edit()
          .putString(KEY_AUTH_TOKEN, value)
          .apply()
    }
}