package com.gitsurfer.gitsurf.ui.main.feed

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.R.drawable
import com.gitsurfer.gitsurf.databinding.FragmentFeedBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.MainActivity
import com.gitsurfer.gitsurf.ui.main.MainViewModel
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedOnScrollListener
import com.gitsurfer.gitsurf.ui.main.feed.view.DividerItemDecorator
import com.gitsurfer.gitsurf.ui.main.feed.view.SwipeClickActions
import com.gitsurfer.gitsurf.ui.main.feed.view.SwipeController

class FeedFragment : BaseFragment<FragmentFeedBinding, FeedViewModel, MainViewModel>() {

  private var fragmentView: View? = null

  override fun getViewModelClass() = FeedViewModel::class.java
  override fun getActivityViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = (activity as MainActivity)
  override fun getLayoutId() = R.layout.fragment_feed

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    if (fragmentView == null) {
      fragmentView = super.onCreateView(inflater, container, savedInstanceState)
      init()
    }
    return fragmentView
  }

  private fun init() {
    binding.viewModel = viewModel
    binding.rvFeed.addItemDecoration(
        DividerItemDecorator(
            context, resources.getDrawable(drawable.divider_drawable, null)
        )
    )
    binding.swipeContainer.setColorSchemeColors(resources.getColor(R.color.colorAccent, null))

    val swipeController = SwipeController(context, object : SwipeClickActions() {
      override fun onLeftClicked(position: Int) {
        showToast("Feed Bookmarked")
        viewModel.bookmarkFeed(position)
      }

      override fun onRightClicked(position: Int) {
        showToast("Repository Starred")
      }
    })
    val itemTouchHelper = ItemTouchHelper(swipeController)
    itemTouchHelper.attachToRecyclerView(binding.rvFeed)

    binding.rvFeed.addItemDecoration(object : ItemDecoration() {
      override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: State
      ) {
        swipeController.onDraw(c)
      }
    })

    listenToLiveData()
    setListeners()
  }

  private fun listenToLiveData() {
    activity?.let { owner ->
      viewModel.getFeed()
          .observe(owner, Observer { feedList ->
            viewModel.updateAdapter(feedList)
            viewModel.adapter.submitList(feedList)
          })

      viewModel.initialProgressLiveData.observe(owner, Observer { isLoading ->
        activityViewModel.progressLiveData.value = isLoading
        binding.swipeContainer.isRefreshing = isLoading
      })

      viewModel.progressLiveData.observe(owner, Observer { isLoading ->
        when (isLoading) {
          true -> binding.pbLoader.visibility = View.VISIBLE
          false -> binding.pbLoader.visibility = View.GONE
        }
      })

      viewModel.exceptionLiveData.observe(owner, Observer { exception ->
        activityViewModel.updateExceptionLiveData(exception)
      })
    }
  }

  private fun setListeners() {
    binding.swipeContainer.setOnRefreshListener {
      viewModel.setNoMoreItemsToLoad(false)
      viewModel.refresh()
    }

    binding.rvFeed.addOnScrollListener(object : FeedOnScrollListener() {
      override fun onLoadMore() {
        if (!viewModel.isNoMoreItemsToLoad()) {
          viewModel.updateProgressLiveData(true)
        }
      }
    })
  }
}