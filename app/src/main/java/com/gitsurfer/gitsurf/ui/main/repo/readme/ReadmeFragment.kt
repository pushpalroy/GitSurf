package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadmeFragment constructor(private val repoUrl: String?) :
  BaseFragment(R.layout.fragment_readme) {

  private val viewModel: ReadmeViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init(view)
  }

  private fun init(view: View) {
    val binding = FragmentReadmeBinding.bind(view)
    binding.viewModel = viewModel

    repoUrl?.let {
      binding.webView.loadUrl(repoUrl)
    }
  }
}