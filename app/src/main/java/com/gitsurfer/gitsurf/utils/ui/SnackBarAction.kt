package com.gitsurfer.gitsurf.utils.ui

import android.view.View

data class SnackBarAction(
  val listener: View.OnClickListener,
  val title: String
)