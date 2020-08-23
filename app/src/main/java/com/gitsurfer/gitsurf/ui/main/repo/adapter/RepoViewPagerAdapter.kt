package com.gitsurfer.gitsurf.ui.main.repo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gitsurfer.gitsurf.data.network.models.response.Repo
import com.gitsurfer.gitsurf.ui.main.repo.files.FilesFragment
import com.gitsurfer.gitsurf.ui.main.repo.readme.ReadmeFragment

class RepoViewPagerAdapter(
  activity: FragmentActivity,
  private val tabs: List<String>,
  private val repo: Repo
) : FragmentStateAdapter(activity) {

  override fun getItemCount(): Int {
    return tabs.size
  }

  override fun createFragment(position: Int): Fragment {
    when (position) {
      0 -> return ReadmeFragment(repo.fullName, repo.defaultBranch)
      1 -> return FilesFragment(repo.fullName, repo.defaultBranch, repo.owner?.login)
    }
    return ReadmeFragment(repo.fullName, repo.defaultBranch)
  }
}