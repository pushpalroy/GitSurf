package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.utils.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadmeFragment constructor(
  private val repoName: String?,
  private val branch: String?
) :
  BaseFragment<ReadmeViewModel, FragmentReadmeBinding>(layout.fragment_readme) {

  override val viewModel: ReadmeViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentReadmeBinding.bind(view)

  override fun init() {
    binding.vm = viewModel

    val readMeFileUrl = "$BASE_URL/repos/$repoName/readme?ref=$branch"
    viewModel.getReadMeSource(readMeFileUrl)

    listen()
  }

  private fun listen() {
    viewModel.readMeSrc.observe(viewLifecycleOwner, Observer { readMeSrc ->
      context?.let {
        binding.markdownView.setMarkDownText(readMeSrc.string())
      }
    })
  }
}

