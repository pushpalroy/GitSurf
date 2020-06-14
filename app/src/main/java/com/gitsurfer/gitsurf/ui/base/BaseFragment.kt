package com.gitsurfer.gitsurf.ui.base

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layoutId: Int) :
  Fragment(layoutId) {

  fun showSnackBar(text: CharSequence?, view: View) {
    text?.let {
      Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()
    }
  }

  fun showToast(
    text: CharSequence?,
    length: Int = Toast.LENGTH_SHORT
  ) {
    text?.let {
      Toast.makeText(requireContext().applicationContext, text, length)
        .show()
    }
  }
}