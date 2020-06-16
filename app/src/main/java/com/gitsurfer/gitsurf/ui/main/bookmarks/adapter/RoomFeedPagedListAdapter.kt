package com.gitsurfer.gitsurf.ui.main.feed.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.ItemRoomFeedBinding
import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.ui.main.feed.paging.RoomFeedPagedListAdapter.RoomFeedViewHolder
import com.gitsurfer.gitsurf.utils.DateUtil
import com.gitsurfer.gitsurf.utils.GithubUtil.getActionFromEventType
import com.gitsurfer.gitsurf.utils.GithubUtil.getDescriptionFromAction
import com.gitsurfer.gitsurf.utils.GithubUtil.getIconFromEventType

class RoomFeedPagedListAdapter : PagedListAdapter<RoomFeed, RoomFeedViewHolder>(
    ROOM_FEED_COMPARATOR
) {

  private var roomFeedItemsList: List<RoomFeed?> = listOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RoomFeedViewHolder {
    return RoomFeedViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layout.item_room_feed, parent, false
        )
    )
  }

  fun setItems(items: List<RoomFeed?>) {
    roomFeedItemsList = items
  }

  fun getFeedItem(position: Int): RoomFeed? {
    return getItem(position)
  }

  override fun getItemCount(): Int {
    return roomFeedItemsList.size
  }

  override fun onBindViewHolder(
    holder: RoomFeedViewHolder,
    position: Int
  ) {
    val feed = getItem(position)
    feed?.let { holder.bind(it) }
  }

  class RoomFeedViewHolder(private val binding: ItemRoomFeedBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(roomFeed: RoomFeed) {
      binding.roomFeed = roomFeed
      val action = getActionFromEventType(roomFeed.type)
      val description = getDescriptionFromAction(action, roomFeed)

      val event =
        roomFeed.actor!!.displayLogin +
            " <b>" +
            action +
            "</b> " +
            description +
            roomFeed.repo!!.name
      HtmlCompat.fromHtml(event, HtmlCompat.FROM_HTML_MODE_LEGACY)
      binding.tvAction.text =
        HtmlCompat.fromHtml(event, HtmlCompat.FROM_HTML_MODE_LEGACY)
      binding.tvTimestamp.text = DateUtil.getTimeAgo(roomFeed.createdAt!!.time)
      binding.ivIcon.setImageResource(getIconFromEventType(roomFeed.type))
    }
  }

  companion object {
    private val ROOM_FEED_COMPARATOR = object : DiffUtil.ItemCallback<RoomFeed>() {
      override fun areItemsTheSame(
        oldItem: RoomFeed,
        newItem: RoomFeed
      ): Boolean =
        oldItem.id == newItem.id

      override fun areContentsTheSame(
        oldItem: RoomFeed,
        newItem: RoomFeed
      ): Boolean =
        newItem == oldItem
    }
  }
}