package com.gitsurfer.mdview

interface ContentChangeListener {
  fun onContentChanged(progress: Int)
  fun onScrollChanged(reachedTop: Boolean, scroll: Int)
}