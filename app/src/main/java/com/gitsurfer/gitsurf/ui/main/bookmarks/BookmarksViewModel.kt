package com.gitsurfer.gitsurf.ui.main.bookmarks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomFeed
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.ui.main.feed.paging.RoomFeedPagedListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookmarksViewModel @ViewModelInject constructor(
  val appRepository: AppRepository
) : BaseViewModel() {

  companion object {
    private const val TAG = "RoomFeed"
  }

  private var roomFeedPagedList: LiveData<PagedList<RoomFeed?>>
  val adapter: RoomFeedPagedListAdapter = RoomFeedPagedListAdapter()

  init {
    val pagedListBuilder: LivePagedListBuilder<Int, RoomFeed?> =
      LivePagedListBuilder(appRepository.getRoomFeeds(), 50)
    roomFeedPagedList = pagedListBuilder.build()
  }

  fun getRoomFeed(): LiveData<PagedList<RoomFeed?>> = roomFeedPagedList

  fun removeBookmark(position: Int) {
    viewModelScope.launch {
      delay(500)
      adapter.getFeedItem(position)
        ?.let { roomFeed ->
          appRepository.removeRoomFeed(roomFeed)
        }
    }
  }

  fun updateAdapter(items: List<RoomFeed?>) {
    adapter.setItems(items)
    adapter.notifyDataSetChanged()
  }
}