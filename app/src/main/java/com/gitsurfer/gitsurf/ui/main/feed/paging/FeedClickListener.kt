package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.navigation.fragment.FragmentNavigator

interface FeedClickListener {
  fun onFeedClicked(
    position: Int,
    extras: FragmentNavigator.Extras
  )
}