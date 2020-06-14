package com.gitsurfer.gitsurf.ui.main.feed

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragment(R.layout.fragment_feed) {

  private val viewModel: FeedViewModel by viewModels()
  private val activityViewModel: MainViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init(view)
  }

  private fun init(view: View) {
    val binding = FragmentFeedBinding.bind(view)
    binding.viewModel = viewModel
    binding.rvFeed.addItemDecoration(
      DividerItemDecorator(
        context, resources.getDrawable(drawable.divider_drawable, null)
      )
    )
    binding.swipeContainer.setColorSchemeColors(resources.getColor(R.color.colorAccent, null))

    val swipeController = SwipeController(
      context, object : SwipeClickActions() {
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

    listenToLiveData(binding)
    setListeners(binding)
  }

  private fun listenToLiveData(binding: FragmentFeedBinding) {
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

      viewModel.feedClickedLiveData.observe(owner, Observer { feedClicked ->

        val bundle = bundleOf(
          "repoId" to feedClicked.id,
          "repoUrl" to feedClicked.repo.url
        )
        findNavController().navigate(R.id.repoFragment, bundle)
      })
    }
  }

  private fun setListeners(binding: FragmentFeedBinding) {
    binding.swipeContainer.setOnRefreshListener {
      viewModel.setNoMoreItemsToLoad(false)
      viewModel.refresh()
    }

    binding.rvFeed.addOnScrollListener(object : ItemOnScrollListener() {
      override fun onLoadMore() {
        if (!viewModel.isNoMoreItemsToLoad()) {
          viewModel.updateProgressLiveData(true)
        }
      }
    })
  }
}