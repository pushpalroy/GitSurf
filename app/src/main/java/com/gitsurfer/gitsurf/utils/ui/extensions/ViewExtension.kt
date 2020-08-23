package com.gitsurfer.gitsurf.utils.ui.extensions

import android.view.View

fun View.toTransitionGroup() = this to transitionName

fun View.setVisible(visible: Boolean) {
  when {
    visible -> this.visibility = View.VISIBLE
    else -> this.visibility = View.GONE
  }
}