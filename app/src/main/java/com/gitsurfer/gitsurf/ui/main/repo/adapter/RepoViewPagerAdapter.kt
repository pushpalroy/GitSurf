package com.gitsurfer.gitsurf.ui.main.repo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gitsurfer.gitsurf.ui.main.repo.readme.ReadmeFragment

class RepoViewPagerAdapter(
  fragment: Fragment,
  private val tabs: List<String>,
  private val repoUrl: String?
) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int {
    return tabs.size
  }

  override fun createFragment(position: Int): Fragment {
    when (position) {
      0 -> return ReadmeFragment(repoUrl)
    }
    return ReadmeFragment(repoUrl)
  }
}