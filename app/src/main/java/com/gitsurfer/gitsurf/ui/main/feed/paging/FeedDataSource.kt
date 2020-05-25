package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.paging.PageKeyedDataSource
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FeedDataSource constructor(
  private val appRepository: AppRepository,
  private val prefUtils: SharedPrefUtils,
  private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Feed>() {

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Feed>
  ) {
    scope.launch {
      val feedListResponse = appRepository.getReceivedFeeds(
          authToken = prefUtils.authToken,
          user = prefUtils.userName,
          page = FIRST_PAGE
      )
      feedListResponse.first?.let { feedList: List<Feed> ->
        callback.onResult(feedList, null, FIRST_PAGE + 1)
      }
    }
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Feed>
  ) {
    scope.launch {
      val feedListResponse = appRepository.getReceivedFeeds(
          authToken = prefUtils.authToken,
          user = prefUtils.userName,
          page = params.key
      )
      feedListResponse.first?.let { feedList: List<Feed> ->
        val key = params.key + 1
        callback.onResult(feedList, key)
      }
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Feed>
  ) {
    scope.launch {
      val feedListResponse = appRepository.getReceivedFeeds(
          authToken = prefUtils.authToken,
          user = prefUtils.userName,
          page = params.key
      )
      feedListResponse.first?.let { feedList: List<Feed> ->
        val key = if (params.key > 1) params.key - 1 else 0
        callback.onResult(feedList, key)
      }
    }
  }

  override fun invalidate() {
    super.invalidate()
    scope.cancel()
  }

  companion object {
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 30
  }
}