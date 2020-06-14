package com.gitsurfer.gitsurf.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gitsurfer.gitsurf.utils.SnackBarAction
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

  private var snackBar: Snackbar? = null
  lateinit var binding: B

  abstract fun getLayoutId(): Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initView()
  }

  private fun initView() {
    binding = DataBindingUtil.setContentView(this, getLayoutId())
  }

  override fun onDestroy() {
    snackBar?.dismiss()
    super.onDestroy()
  }

  protected fun showSnackBarMessage(
    message: Int?,
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
        snackBar = Snackbar.make(binding.root, it, duration)
        snackBar?.show()
        snackBar
      }
    }
  }

  protected fun showSnackBarWithAction(
    message: String,
    snackBarAction: SnackBarAction,
    duration: Int = Snackbar.LENGTH_SHORT
  ) {
    Snackbar.make(binding.root, message, duration)
      .setAction(snackBarAction.title, snackBarAction.listener)
      .show()
  }
}