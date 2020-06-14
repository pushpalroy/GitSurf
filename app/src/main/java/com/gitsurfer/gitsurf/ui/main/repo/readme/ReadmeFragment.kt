package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStoreOwner
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadmeFragment constructor(private val repoUrl: String?) :
  BaseFragment<FragmentReadmeBinding>() {

  private var fragmentView: View? = null
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = activity as MainActivity
  override fun getLayoutId(): Int = R.layout.fragment_readme
  private val viewModel: ReadmeViewModel by viewModels()

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

    repoUrl?.let {
      binding.webView.loadUrl(repoUrl)
    }
  }
}