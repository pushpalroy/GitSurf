package com.gitsurfer.gitsurf.ui.main.feed.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.databinding.ItemFeedBinding
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.ui.main.feed.paging.FeedPagedListAdapter.FeedViewHolder
import com.gitsurfer.gitsurf.utils.DateUtil
import com.gitsurfer.gitsurf.utils.GithubUtil.getActionFromEventType
import com.gitsurfer.gitsurf.utils.GithubUtil.getDescriptionFromAction
import com.gitsurfer.gitsurf.utils.GithubUtil.getIconFromEventType

class FeedPagedListAdapter : PagedListAdapter<Feed, FeedViewHolder>(
    FEED_COMPARATOR
) {

  private var feedItemsList: List<Feed> = listOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): FeedViewHolder {
    return FeedViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layout.item_feed, parent, false
        )
    )
  }

  fun setItems(items: List<Feed>) {
    feedItemsList = items
  }

  fun getFeedItem(position: Int): Feed? {
    return getItem(position)
  }

  override fun getItemCount(): Int {
    return feedItemsList.size
  }

  override fun onBindViewHolder(
    holder: FeedViewHolder,
    position: Int
  ) {
    val feed = getItem(position)
    feed?.let { holder.bind(it) }
  }

  class FeedViewHolder(private val binding: ItemFeedBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(feed: Feed) {
      binding.feed = feed
      val action = getActionFromEventType(feed.type)
      val description = getDescriptionFromAction(action, feed)

      val event =
        feed.actor.displayLogin +
            " <b>" +
            action +
            "</b> " +
            description +
            feed.repo.name
      HtmlCompat.fromHtml(event, HtmlCompat.FROM_HTML_MODE_LEGACY)
      binding.tvAction.text =
        HtmlCompat.fromHtml(event, HtmlCompat.FROM_HTML_MODE_LEGACY)
      binding.tvTimestamp.text = DateUtil.getTimeAgo(feed.createdAt.time)
      binding.ivIcon.setImageResource(getIconFromEventType(feed.type))
    }
  }

  companion object {
    private val FEED_COMPARATOR = object : DiffUtil.ItemCallback<Feed>() {
      override fun areItemsTheSame(
        oldItem: Feed,
        newItem: Feed
      ): Boolean =
        oldItem.id == newItem.id

      override fun areContentsTheSame(
        oldItem: Feed,
        newItem: Feed
      ): Boolean =
        newItem == oldItem
    }
  }
}