package com.gitsurfer.gitsurf.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.NetworkManager
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.ui.main.feed.adapter.FeedAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
  private val appRepository: AppRepository,
  private val networkManager: NetworkManager,
  private val prefUtils: SharedPrefUtils
) : BaseViewModel() {

  val adapter: FeedAdapter = FeedAdapter(this)

  companion object {
    private const val TAG = "Feed"
  }

  private val _feedListLiveData = MutableLiveData<List<Feed>>()
  val feedListLiveData: LiveData<List<Feed>>
    get() = _feedListLiveData

  fun updateAdapter(items: List<Feed>) {
    adapter.setItems(items)
    adapter.notifyDataSetChanged()
  }

  fun loadPersonalFeed() {
    updateLiveDataProgress(progress = true)
    viewModelScope.launch {
      when {
        networkManager.hasInternetAccess() -> {
          val feedListResponse = appRepository.getReceivedFeeds(
              prefUtils.authToken,
              prefUtils.userName
          )

          feedListResponse.first?.let { feedList: List<Feed> ->
            updateLiveDataProgress(progress = false)
            _feedListLiveData.value = feedList
          }
        }
      }
    }
  }
}