package com.gitsurfer.gitsurf.ui.main.repo.files.code

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.FragmentCodeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.MainActivity
import com.gitsurfer.mdview.ContentChangeListener
import com.gitsurfer.mdview.MdView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class CodeFragment : BaseFragment<CodeViewModel, FragmentCodeBinding>(layout.fragment_code),
  ContentChangeListener {

  override val viewModel: CodeViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentCodeBinding.bind(view)

  override fun init() {
    setToolbarTitle()
    initMarkDown()
    fetchCode()
    listen()
  }

  private fun listen() {
    viewModel.codeSrc.observe(viewLifecycleOwner, Observer { codeSrc ->
      context?.let {
        try {
          binding.markdownView.setMarkDownText(codeSrc.string())
          hideLoader()
        } catch (ex: IOException) {
          Timber.tag(MdView.TAG)
            .e(ex)
        }
      }
    })
  }

  private fun initMarkDown() {
    binding.markdownView.isOpenUrlInBrowser = true
    binding.markdownView.setEnableNestedScrolling(true)
    binding.markdownView.setInterceptTouch(true)
    binding.markdownView.setOnContentChangedListener(this)
  }

  private fun setToolbarTitle() {
    (requireActivity() as MainActivity).updateTitle(
      arguments?.getString("fileName", "")
    )
  }

  private fun fetchCode() {
    showLoader()
    viewModel.fetchCode(
      arguments?.getString("fileUrl", "")
    )
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
    binding.pbLoader.visibility = View.VISIBLE
  }

  private fun hideLoader() {
    binding.pbLoader.visibility = View.GONE
  }
}

