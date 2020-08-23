package com.gitsurfer.gitsurf.ui.main.feed

import android.graphics.Canvas
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.R.drawable
import com.gitsurfer.gitsurf.databinding.FragmentFeedBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.MainViewModel
import com.gitsurfer.gitsurf.utils.ui.DividerItemDecorator
import com.gitsurfer.gitsurf.utils.ui.ItemOnScrollListener
import com.gitsurfer.gitsurf.utils.ui.SwipeClickActions
import com.gitsurfer.gitsurf.utils.ui.SwipeController
import com.gitsurfer.gitsurf.utils.ui.extensions.waitForTransition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragment<FeedViewModel, FragmentFeedBinding>(R.layout.fragment_feed) {

  override val viewModel: FeedViewModel by viewModels()
  private val activityViewModel: MainViewModel by activityViewModels()
  override fun getViewBinding(view: View) = FragmentFeedBinding.bind(view)

  override fun init() {
    binding.viewModel = viewModel
    initRecyclerView()
    setListeners()
    listenToLiveData()
    waitForTransition(binding.rvFeed)
  }

  private fun listenToLiveData() {
    activity?.let { owner ->
      viewModel.getFeed()
        .observe(owner, Observer { feedList ->
          viewModel.updateAdapter(feedList)
          viewModel.adapter.submitList(feedList)
        })

      viewModel.initialProgressLiveData.observe(owner, Observer { isLoading ->
        activityViewModel.updateProgressLiveData(progress = isLoading)
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

      viewModel.feedClickEvent.observe(owner, Observer { feedWithExtras ->
        findNavController().navigate(
          FeedFragmentDirections.openRepoFromFeed(feedWithExtras.first),
          feedWithExtras.second
        )
      })
    }
  }

  private fun setListeners() {
    binding.swipeContainer.setOnRefreshListener {
      viewModel.setNoMoreItemsToLoad(false)
      viewModel.refresh()
    }

    binding.rvFeed.addOnScrollListener(object : ItemOnScrollListener() {
      override fun onLoadMore() {
        if (!viewModel.isNoMoreItemsToLoad()) {
          viewModel.updateProgressLiveData(progress = true)
        }
      }
    })
  }

  private fun initRecyclerView() {
    binding.rvFeed.addItemDecoration(
      DividerItemDecorator(
        context, resources.getDrawable(drawable.divider_drawable, null)
      )
    )
    binding.swipeContainer.setColorSchemeColors(resources.getColor(R.color.colorAccent, null))

    val swipeController = initSwipeRefresher()
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
    waitForTransition(binding.rvFeed)
  }

  private fun initSwipeRefresher(): SwipeController {
    return SwipeController(
      context, object : SwipeClickActions() {
        override fun onLeftClicked(position: Int) {
          showToast("Feed Bookmarked")
          viewModel.bookmarkFeed(position)
        }

        override fun onRightClicked(position: Int) {
          showToast("Repository Starred")
        }
      })
  }
}