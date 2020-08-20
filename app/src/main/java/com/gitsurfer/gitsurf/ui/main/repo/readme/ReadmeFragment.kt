package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.utils.BASE_URL
import com.gitsurfer.mdview.ContentChangeListener
import com.gitsurfer.mdview.MdView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class ReadmeFragment constructor(
  private val repoName: String?,
  private val branch: String?
) :
  BaseFragment<ReadmeViewModel, FragmentReadmeBinding>(layout.fragment_readme),
  ContentChangeListener {

  override val viewModel: ReadmeViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentReadmeBinding.bind(view)

  override fun init() {
    binding.vm = viewModel
    fetchReadMe()
    initMarkDown()
    setListeners()
    listen()
  }

  private fun listen() {
    viewModel.readMeSrc.observe(viewLifecycleOwner, Observer { readMeSrc ->
      context?.let {
        try {
          binding.markdownView.setMarkDownText(readMeSrc.string())
          hideLoader()
        } catch (ex: IOException) {
          Timber.tag(MdView.TAG)
            .e(ex)
        }
      }
    })
  }

  private fun setListeners() {
    binding.swipeContainer.setOnRefreshListener {
      fetchReadMe()
    }
  }

  private fun initMarkDown() {
    binding.markdownView.isOpenUrlInBrowser = true
    binding.markdownView.setEnableNestedScrolling(true)
    binding.markdownView.setInterceptTouch(true)
    binding.markdownView.setOnContentChangedListener(this)
  }

  private fun fetchReadMe() {
    showLoader()
    val readMeFileUrl = "$BASE_URL/repos/$repoName/readme?ref=$branch"
    viewModel.getReadMeSource(readMeFileUrl)
  }

  override fun onContentChanged(progress: Int) {
    Timber.d("OnContentChanged: %s", progress)
  }

  override fun onScrollChanged(
    reachedTop: Boolean,
    scroll: Int
  ) {
    Timber.d("onScrollChanged: %s, reachedTop: %s", scroll, reachedTop)
  }

  private fun showLoader() {
    binding.swipeContainer.isRefreshing = true
  }

  private fun hideLoader() {
    binding.swipeContainer.isRefreshing = false
  }
}

