package com.gitsurfer.gitsurf.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.network.models.response.toRoomFeed
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedDataSource.Companion.INITIAL_LOAD_SIZE_HINT
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedDataSource.Companion.PAGE_SIZE
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedDataSourceFactory
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedPagedListAdapter
import com.gitsurfer.gitsurf.utils.SingleLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FeedViewModel @Inject constructor(
  private val appRepository: AppRepository,
  prefUtils: SharedPrefUtils
) : BaseViewModel() {

  companion object {
    private const val TAG = "Feed"
  }

  private var feedPagedList: LiveData<PagedList<Feed>>
  val adapter: FeedPagedListAdapter = FeedPagedListAdapter()
  val initialProgressLiveData = SingleLiveData<Boolean>()
  private var noMoreItemsToLoad: Boolean = false

  private var feedDataSourceFactory = FeedDataSourceFactory(
      appRepository = appRepository,
      prefUtils = prefUtils,
      scope = viewModelScope,
      viewModel = this
  )

  init {
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
        .setPageSize(PAGE_SIZE)
        .build()
    feedPagedList = LivePagedListBuilder(feedDataSourceFactory, config).build()
  }

  fun getFeed(): LiveData<PagedList<Feed>> = feedPagedList

  fun refresh() {
    feedDataSourceFactory.getFeedLiveDataSource().value?.invalidate()
  }

  internal fun updateInitialProgressLiveData(progress: Boolean) {
    initialProgressLiveData.call(progress)
  }

  fun setNoMoreItemsToLoad(flag: Boolean) {
    noMoreItemsToLoad = flag
  }

  fun isNoMoreItemsToLoad(): Boolean {
    return noMoreItemsToLoad
  }

  fun updateAdapter(items: List<Feed>) {
    adapter.setItems(items)
    adapter.notifyDataSetChanged()
  }

  fun bookmarkFeed(position: Int) {
    viewModelScope.launch {
      adapter.getFeedItem(position)
          ?.let { roomFeed ->
            val res = appRepository.insertFeedLocal(
                roomFeed.toRoomFeed()
            )
            Timber.tag(TAG)
                .i(res.toString())
          }
    }
  }
}