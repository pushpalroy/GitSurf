package com.example.gitsurfer.model.utils

import android.content.SharedPreferences

const val KEY_USER_NAME = "KEY_USER_NAME"

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
}