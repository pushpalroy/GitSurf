package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.utils.BASE_URL
import com.gitsurfer.mdview.MdView
import com.gitsurfer.mdview.MdView.OnContentChangedListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReadmeFragment constructor(
  private val repoName: String?,
  private val branch: String?
) :
    BaseFragment<ReadmeViewModel, FragmentReadmeBinding>(layout.fragment_readme),
    OnContentChangedListener {

  override val viewModel: ReadmeViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentReadmeBinding.bind(view)

  override fun init() {
    binding.vm = viewModel

    val readMeFileUrl = "$BASE_URL/repos/$repoName/readme?ref=$branch"
    viewModel.getReadMeSource(readMeFileUrl)
    binding.markdownView.isOpenUrlInBrowser = true
    binding.markdownView.setEnableNestedScrolling(true)
    binding.markdownView.setInterceptTouch(true)
    binding.markdownView.setOnContentChangedListener(this)

    listen()
  }

  private fun listen() {
    viewModel.readMeSrc.observe(viewLifecycleOwner, Observer { readMeSrc ->
      context?.let {
        binding.markdownView.setMarkDownText(readMeSrc.string())
      }
    })
  }

  override fun onContentChanged(progress: Int) {
    Timber.tag(MdView.TAG)
        .d("OnContentChanged: %s", progress)
  }

  override fun onScrollChanged(
    reachedTop: Boolean,
    scroll: Int
  ) {
    Timber.tag(MdView.TAG)
        .d("onScrollChanged: %s, reachedTop: %s", scroll, reachedTop)
  }
}

