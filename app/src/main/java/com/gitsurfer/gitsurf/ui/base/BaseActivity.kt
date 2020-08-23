package com.gitsurfer.gitsurf.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.gitsurfer.gitsurf.utils.ui.SnackBarAction
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

  protected abstract val viewModel: VM
  protected lateinit var binding: VB
  private var snackBar: Snackbar? = null

  abstract fun getViewBinding(): VB
  abstract fun init()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = getViewBinding()
    setContentView(binding.root)
    init()
  }

  protected fun showSnackBar(
    message: Int?,
    view: View,
    duration: Int = Snackbar.LENGTH_SHORT
  ) {
    snackBar = snackBar?.let {
      message?.let { msg -> it.setText(msg) }
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

  override fun onDestroy() {
    snackBar?.dismiss()
    super.onDestroy()
  }
}