package com.gitsurfer.gitsurf.ui.main.bookmarks

import android.graphics.Canvas
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.R.drawable
import com.gitsurfer.gitsurf.databinding.FragmentBookmarksBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.utils.ui.DividerItemDecorator
import com.gitsurfer.gitsurf.utils.ui.SwipeClickActions
import com.gitsurfer.gitsurf.utils.ui.SwipeController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment :
  BaseFragment<BookmarksViewModel, FragmentBookmarksBinding>(R.layout.fragment_bookmarks) {

  override val viewModel: BookmarksViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentBookmarksBinding.bind(view)

  override fun init() {
    binding.viewModel = viewModel
    binding.rvBookmarks.addItemDecoration(
      DividerItemDecorator(
        context, resources.getDrawable(drawable.divider_drawable, null)
      )
    )

    val swipeController = SwipeController(
      context, object : SwipeClickActions() {
        override fun onLeftClicked(position: Int) {
          showToast("Bookmark Removed")
          viewModel.removeBookmark(position)
        }

        override fun onRightClicked(position: Int) {
          showToast("Repository Starred")
        }
      })
    val itemTouchHelper = ItemTouchHelper(swipeController)
    itemTouchHelper.attachToRecyclerView(binding.rvBookmarks)

    binding.rvBookmarks.addItemDecoration(object : ItemDecoration() {
      override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: State
      ) {
        swipeController.onDraw(c)
      }
    })

    listenToLiveData()
  }

  private fun listenToLiveData() {
    activity?.let { owner ->
      viewModel.getRoomFeed()
        .observe(owner, Observer { roomFeedList ->
          viewModel.updateAdapter(roomFeedList)
          viewModel.adapter.submitList(roomFeedList)
        })
    }
  }
}