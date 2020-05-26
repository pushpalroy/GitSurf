package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.paging.PageKeyedDataSource
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.main.feed.FeedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
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
    showProgress()
    scope.launch {
      val feedListResponse = getReceivedFeeds(FIRST_PAGE)
      feedListResponse.first?.let { feedList: List<Feed> ->
        hideProgress()
        callback.onResult(feedList, null, FIRST_PAGE + 1)
      }
      feedListResponse.second?.let { exception ->
        Timber.e(exception)
        viewModel.updateLiveDataException(exception)
      }
    }
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Feed>
  ) {
    showProgress()
    scope.launch {
      val feedListResponse = getReceivedFeeds(params.key)
      feedListResponse.first?.let { feedList: List<Feed> ->
        hideProgress()
        val key = params.key + 1
        callback.onResult(feedList, key)
      }
      feedListResponse.second?.let { exception ->
        Timber.e(exception)
        viewModel.updateLiveDataException(exception)
      }
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Feed>
  ) {
    showProgress()
    scope.launch {
      val feedListResponse = getReceivedFeeds(params.key)
      feedListResponse.first?.let { feedList: List<Feed> ->
        hideProgress()
        val key = if (params.key > 1) params.key - 1 else 0
        callback.onResult(feedList, key)
      }
      feedListResponse.second?.let { exception ->
        Timber.e(exception)
        viewModel.updateLiveDataException(exception)
      }
    }
  }

  private suspend fun getReceivedFeeds(page: Int?): Pair<List<Feed>?, Exception?> {
    return appRepository.getReceivedFeeds(
        authToken = prefUtils.authToken,
        user = prefUtils.userName,
        page = page,
        perPage = PAGE_SIZE
    )
  }

  private fun showProgress() {
    viewModel.updateLiveDataProgress(progress = true)
  }

  private fun hideProgress() {
    viewModel.updateLiveDataProgress(progress = false)
  }

  override fun invalidate() {
    super.invalidate()
    scope.cancel()
  }

  companion object {
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 10
    const val INITIAL_LOAD_SIZE_HINT = 10
  }
}