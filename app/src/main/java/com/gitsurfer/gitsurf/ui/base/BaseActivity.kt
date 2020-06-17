package com.gitsurfer.gitsurf.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gitsurfer.gitsurf.utils.ui.SnackBarAction
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

  private var snackBar: Snackbar? = null

  override fun onDestroy() {
    snackBar?.dismiss()
    super.onDestroy()
  }

  protected fun showSnackBarMessage(
    message: Int?,
    view: View,
    duration: Int = Snackbar.LENGTH_SHORT
  ) {
    snackBar = snackBar?.let {
      message?.let { it1 -> it.setText(it1) }
      if (!it.isShown) {
        it.show()
      }
      it
    } ?: run {
      message?.let {
        snackBar = Snackbar.make(view, it, duration)
        snackBar?.show()
        snackBar
      }
    }
  }

  protected fun showSnackBarWithAction(
    message: String,
    snackBarAction: SnackBarAction,
    duration: Int = Snackbar.LENGTH_SHORT,
    view: View
  ) {
    Snackbar.make(view, message, duration)
      .setAction(snackBarAction.title, snackBarAction.listener)
      .show()
  }

  protected fun dismissSnackBar() {
    snackBar?.let { snack ->
      if (snack.isShown) {
        snack.dismiss()
      }
    }
  }
}