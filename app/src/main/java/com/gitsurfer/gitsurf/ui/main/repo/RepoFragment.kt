package com.gitsurfer.gitsurf.ui.main.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.FragmentRepoBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.repo.adapter.RepoViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoFragment : BaseFragment<RepoViewModel, FragmentRepoBinding>(layout.fragment_repo) {

  companion object {
    val tabs = listOf("README", "FILES", "COMMITS")
  }

  override val viewModel: RepoViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentRepoBinding.bind(view)

  private val args: RepoFragmentArgs by navArgs()

  override fun init() {
    binding.feed = args.feed
    initUi()
    listenToLiveData()
    fetchRepo()
  }

  private fun fetchRepo() {
    viewModel.fetchRepoDetails(args.feed.repo.name)
  }

  private fun listenToLiveData() {
    viewModel.repoLiveData.observe(
      this, Observer { repo ->
        binding.pager.adapter = RepoViewPagerAdapter(
          requireActivity(), tabs, repo
        )
        TabLayoutMediator(
          binding.tabLayout,
          binding.pager
        ) { tab, position ->
          tab.text = tabs[position]
        }.attach()
      })
  }

  private fun initUi() {
    sharedElementEnterTransition =
      TransitionInflater.from(context)
        .inflateTransition(android.R.transition.move)
    binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    binding.executePendingBindings()
  }
}