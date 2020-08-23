package com.gitsurfer.gitsurf.ui.repo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.gitsurfer.gitsurf.data.network.models.response.Feed
import com.gitsurfer.gitsurf.databinding.ActivityRepoBinding
import com.gitsurfer.gitsurf.ui.base.BaseActivity
import com.gitsurfer.gitsurf.ui.repo.adapter.RepoViewPagerAdapter
import com.gitsurfer.gitsurf.utils.constants.FEED_CLICKED
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_repo.toolbar

@AndroidEntryPoint
class RepoActivity : BaseActivity<RepoViewModel, ActivityRepoBinding>() {

  override val viewModel: RepoViewModel by viewModels()
  override fun getViewBinding() = ActivityRepoBinding.inflate(layoutInflater)

  companion object {
    val tabs = listOf("README", "FILES", "COMMITS")

    @JvmStatic
    fun getBundle(
      feed: Feed
    ): Bundle {
      return Bundle().apply {
        putParcelable(FEED_CLICKED, feed)
      }
    }
  }

  override fun init() {
    intent.getParcelableExtra<Feed>(FEED_CLICKED)?.let {
      binding.feed = it
      initUi(it.repo.name)
      listenToLiveData()
      fetchRepo(it.repo.name)
    }
  }

  private fun fetchRepo(repoName: String) {
    viewModel.fetchRepoDetails(repoName)
  }

  private fun listenToLiveData() {
    viewModel.repoLiveData.observe(
      this, Observer { repo ->
        binding.pager.adapter = RepoViewPagerAdapter(
          this, tabs, repo
        )
        TabLayoutMediator(
          binding.tabLayout,
          binding.pager
        ) { tab, position ->
          tab.text = tabs[position]
        }.attach()
      })
  }

  private fun initUi(repoName: String) {
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeButtonEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = repoName
    binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}