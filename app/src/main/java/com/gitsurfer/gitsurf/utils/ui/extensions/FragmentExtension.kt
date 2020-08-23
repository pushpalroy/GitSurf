package com.gitsurfer.gitsurf.utils.ui.extensions

import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment

fun Fragment.waitForTransition(targetView: View) {
  postponeEnterTransition()
  targetView.doOnPreDraw { startPostponedEnterTransition() }
}

fun Fragment.hideKeyboard() {
  activity?.hideKeyboard()
}