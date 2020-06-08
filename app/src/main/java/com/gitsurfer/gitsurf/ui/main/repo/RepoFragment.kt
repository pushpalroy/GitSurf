package com.gitsurfer.gitsurf.ui.main.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.widget.ViewPager2
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentRepoBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.MainActivity
import com.gitsurfer.gitsurf.ui.main.MainViewModel
import com.gitsurfer.gitsurf.ui.main.repo.adapter.RepoViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RepoFragment :
    BaseFragment<FragmentRepoBinding, RepoViewModel, MainViewModel>() {

  private var fragmentView: View? = null

  override fun getViewModelClass() = RepoViewModel::class.java
  override fun getActivityViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = activity as MainActivity
  override fun getLayoutId(): Int = R.layout.fragment_repo

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
    val tabs = listOf("README", "FILES", "COMMITS", "RELEASES", "CONTRIBUTORS")
    binding.viewModel = viewModel
    binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    binding.pager.adapter = RepoViewPagerAdapter(this, tabs, arguments?.getString("repoUrl", ""))
    TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
      tab.text = "OBJECT ${(position + 1)}"
    }.attach()
  }
}