package com.gitsurfer.gitsurf.utils

import android.view.View

data class SnackBarAction(
  val listener: View.OnClickListener,
  val title: String
)