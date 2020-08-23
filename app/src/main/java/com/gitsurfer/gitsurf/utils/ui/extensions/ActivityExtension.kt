package com.gitsurfer.gitsurf.utils.ui.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  val view = findViewById<View>(android.R.id.content)
  view?.let {
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
}