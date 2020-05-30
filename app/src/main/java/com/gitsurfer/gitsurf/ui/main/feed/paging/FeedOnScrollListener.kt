package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class FeedOnScrollListener : RecyclerView.OnScrollListener() {

  override fun onScrolled(
    rv: RecyclerView,
    dx: Int,
    dy: Int
  ) {
    super.onScrolled(rv, dx, dy)

    val visibleItemCount = rv.childCount
    val totalItemCount = rv.layoutManager?.itemCount
    val pastVisibleItems = (rv.layoutManager as LinearLayoutManager)
        .findFirstVisibleItemPosition()

    totalItemCount?.let {
      if (pastVisibleItems != 0 &&
          (visibleItemCount + pastVisibleItems) >= totalItemCount
      ) {
        onLoadMore()
      }
    }
  }

  abstract fun onLoadMore()
}