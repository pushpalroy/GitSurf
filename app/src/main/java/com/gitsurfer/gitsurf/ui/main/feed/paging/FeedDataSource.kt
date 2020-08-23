package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.paging.PageKeyedDataSource
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.models.response.Feed
import com.gitsurfer.gitsurf.data.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.main.feed.FeedViewModel
import com.gitsurfer.gitsurf.utils.constants.TOKEN_PREFIX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class FeedDataSource constructor(
  private val appRepository: AppRepository,
  private val prefUtils: SharedPrefUtils,
  private val scope: CoroutineScope,
  private val viewModel: FeedViewModel
) : PageKeyedDataSource<Int, Feed>() {

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Feed>
  ) {
    showInitialProgress()
    scope.launch {
      val feedListResponse = getReceivedFeeds(FIRST_PAGE)
      feedListResponse.first?.let { feedList: List<Feed> ->
        hideInitialProgress()
        callback.onResult(feedList, null, FIRST_PAGE)
      }
      feedListResponse.second?.let { exception ->
        Timber.e(exception)
        viewModel.updateExceptionLiveData(exception)
      }
    }
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Feed>
  ) {
    scope.launch {
      val feedListResponse = getReceivedFeeds(params.key)
      feedListResponse.first?.let { feedList: List<Feed> ->
        val key = params.key + 1
        callback.onResult(feedList, key)
        newItemsLoaded()

        if (feedList.isEmpty()) {
          setNoMoreItemsToLoad()
        }
      }
      feedListResponse.second?.let { exception ->
        Timber.e(exception)
        viewModel.updateExceptionLiveData(exception)
      }
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Feed>
  ) {
    scope.launch {
      val feedListResponse = getReceivedFeeds(params.key)
      feedListResponse.first?.let { feedList: List<Feed> ->
        val key = if (params.key > 1) params.key - 1 else 0
        callback.onResult(feedList, key)
      }
      feedListResponse.second?.let { exception ->
        Timber.e(exception)
        viewModel.updateExceptionLiveData(exception)
      }
    }
  }

  private suspend fun getReceivedFeeds(page: Int?): Pair<List<Feed>?, Exception?> {
    return appRepository.getReceivedFeeds(
      authToken = TOKEN_PREFIX + prefUtils.authToken,
      user = prefUtils.userName,
      page = page,
      perPage = PAGE_SIZE
    )
  }

  private fun showInitialProgress() {
    viewModel.updateInitialProgressLiveData(progress = true)
  }

  private fun hideInitialProgress() {
    viewModel.updateInitialProgressLiveData(progress = false)
  }

  private fun newItemsLoaded() {
    viewModel.updateProgressLiveData(progress = false)
  }

  private fun setNoMoreItemsToLoad() {
    viewModel.setNoMoreItemsToLoad(true)
  }

  companion object {
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE_HINT = 20
  }
}