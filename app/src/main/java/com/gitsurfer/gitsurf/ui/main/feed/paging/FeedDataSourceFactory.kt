package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.models.response.Feed
import com.gitsurfer.gitsurf.data.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.main.feed.FeedViewModel
import kotlinx.coroutines.CoroutineScope

class FeedDataSourceFactory constructor(
  private val appRepository: AppRepository,
  private val prefUtils: SharedPrefUtils,
  private val scope: CoroutineScope,
  private val viewModel: FeedViewModel
) : DataSource.Factory<Int, Feed>() {

  private val feedLiveDataSource = MutableLiveData<FeedDataSource>()

  override fun create(): DataSource<Int, Feed> {
    val feedDataSource = FeedDataSource(
        appRepository = appRepository,
        prefUtils = prefUtils,
        scope = scope,
        viewModel = viewModel
    )
    feedLiveDataSource.postValue(feedDataSource)
    return feedDataSource
  }

  fun getFeedLiveDataSource(): MutableLiveData<FeedDataSource> {
    return feedLiveDataSource
  }
}