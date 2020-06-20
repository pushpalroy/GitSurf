package com.gitsurfer.gitsurf.ui.main.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.FragmentRepoBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.repo.adapter.RepoViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoFragment : BaseFragment<RepoViewModel, FragmentRepoBinding>(R.layout.fragment_repo) {

  override val viewModel: RepoViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentRepoBinding.bind(view)

  companion object {
    val tabs = listOf("README", "FILES", "COMMITS", "RELEASES", "CONTRIBUTORS")
  }

  override fun init() {
    binding.vm = viewModel
    binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    listenToLiveData()
    fetchRepo()
  }

  private fun fetchRepo() {
    viewModel.fetchRepoDetails(
      arguments?.getString("repoOwner", ""),
      arguments?.getString("repoName", "")
    )
  }

  private fun listenToLiveData() {
    viewModel.repoLiveData.observe(viewLifecycleOwner, Observer { repo ->
      binding.pager.adapter = RepoViewPagerAdapter(
        this,
        tabs,
        repo
      )
      TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
        tab.text = tabs[position]
      }.attach()
    })
  }

  override fun onDetach() {
    super.onDetach()
    findNavController().popBackStack()
  }
}