package com.gitsurfer.gitsurf.ui.main.repo.readme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentReadmeBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.MainActivity
import com.gitsurfer.gitsurf.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadmeFragment constructor(private val repoUrl: String?) :
  BaseFragment<FragmentReadmeBinding, ReadmeViewModel, MainViewModel>() {

  private var fragmentView: View? = null

  override fun getViewModelClass() = ReadmeViewModel::class.java
  override fun getActivityViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = activity as MainActivity
  override fun getLayoutId(): Int = R.layout.fragment_readme

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