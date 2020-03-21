package com.gitsurfer.gitsurf.utils

import android.app.Activity
import android.content.Context
import android.os.ResultReceiver
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

fun Activity.hideKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  val view = findViewById<View>(android.R.id.content)
  view?.let {
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
}

fun Fragment.hideKeyboard() {
  activity?.hideKeyboard()
}

fun Context.hideKeyboard(resultReceiver: ResultReceiver? = null) {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  if (this is Activity) {
    val view = this.findViewById<View>(android.R.id.content)
    view?.let {
      imm.hideSoftInputFromWindow(view.windowToken, 0, resultReceiver)
    }
  }
}

fun View.setVisible(visible: Boolean) {
  when {
    visible -> this.visibility = View.VISIBLE
    else -> this.visibility = View.GONE
  }
}

fun getViewVisibility(visible: Boolean): Int {
  return when {
    visible -> View.VISIBLE
    else -> View.GONE
  }
}

fun EditText.clearErrorOnTextChange(usernameInputText: TextInputLayout? = null) {
  this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(
      s: CharSequence?,
      start: Int,
      count: Int,
      after: Int
    ) {
    }

    override fun onTextChanged(
      s: CharSequence?,
      start: Int,
      before: Int,
      count: Int
    ) {
      usernameInputText?.error = null
      usernameInputText?.isErrorEnabled = false
      this@clearErrorOnTextChange.error = null
    }
  })
}