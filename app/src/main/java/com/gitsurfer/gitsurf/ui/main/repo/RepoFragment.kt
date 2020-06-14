package com.gitsurfer.gitsurf.ui.main.repo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentRepoBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.repo.adapter.RepoViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoFragment : BaseFragment(R.layout.fragment_repo) {

  private val viewModel: RepoViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init(view)
  }

  private fun init(view: View) {
    val tabs = listOf("README", "FILES", "COMMITS", "RELEASES", "CONTRIBUTORS")
    val binding = FragmentRepoBinding.bind(view)
    binding.viewModel = viewModel
    binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    binding.pager.adapter = RepoViewPagerAdapter(this, tabs, arguments?.getString("repoUrl", ""))
    TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
      tab.text = "OBJECT ${(position + 1)}"
    }.attach()
  }
}