package com.gitsurfer.gitsurf.ui.main.feed.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope

class FeedDataSourceFactory constructor(
  private val appRepository: AppRepository,
  private val prefUtils: SharedPrefUtils,
  private val scope: CoroutineScope
) : DataSource.Factory<Int, Feed>() {

  val feedLiveDataSource = MutableLiveData<FeedDataSource>()

  override fun create(): DataSource<Int, Feed> {
    val feedDataSource = FeedDataSource(
        appRepository = appRepository,
        prefUtils = prefUtils,
        scope = scope
    )
    feedLiveDataSource.postValue(feedDataSource)
    return feedDataSource
  }
}