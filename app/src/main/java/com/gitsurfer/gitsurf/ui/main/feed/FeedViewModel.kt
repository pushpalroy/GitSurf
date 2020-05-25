package com.gitsurfer.gitsurf.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedDataSource
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedDataSourceFactory
import com.gitsurfer.gitsurf.ui.main.feed.paging.adapter.FeedAdapter
import javax.inject.Inject

class FeedViewModel @Inject constructor(
  appRepository: AppRepository,
  prefUtils: SharedPrefUtils
) : BaseViewModel() {

  val adapter: FeedAdapter = FeedAdapter()

  companion object {
    private const val TAG = "Feed"
  }

  var feedPagedList: LiveData<PagedList<Feed>>

  init {
    val itemDataSourceFactory = FeedDataSourceFactory(
        appRepository = appRepository,
        prefUtils = prefUtils,
        scope = viewModelScope
    )
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(30)
        .setPageSize(FeedDataSource.PAGE_SIZE)
        .build()
    feedPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()
  }

  fun getFeed(): LiveData<PagedList<Feed>> = feedPagedList

  fun updateAdapter(items: List<Feed>) {
    adapter.setItems(items)
    adapter.notifyDataSetChanged()
  }
}