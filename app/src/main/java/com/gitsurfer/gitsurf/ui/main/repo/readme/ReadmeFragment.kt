package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.view.View
import androidx.fragment.app.viewModels
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.utils.GITHUB_BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadmeFragment constructor(
  private val repoName: String?,
  private val branch: String?
) :
  BaseFragment<ReadmeViewModel, FragmentReadmeBinding>(R.layout.fragment_readme) {

  override val viewModel: ReadmeViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentReadmeBinding.bind(view)

  override fun init() {
    binding.vm = viewModel

    val readMeUrl = "$GITHUB_BASE_URL$repoName/blob/$branch/README.md"
    binding.webView.loadUrl(readMeUrl)
  }
}