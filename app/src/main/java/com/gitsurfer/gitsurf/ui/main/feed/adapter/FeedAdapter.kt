package com.gitsurfer.gitsurf.ui.main.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.ItemFeedBinding
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.ui.main.feed.FeedViewModel
import com.gitsurfer.gitsurf.ui.main.feed.adapter.FeedAdapter.FeedViewHolder

class FeedAdapter constructor(
  private val viewModel: FeedViewModel
) : RecyclerView.Adapter<FeedViewHolder>() {

  private var feedItemsList: List<Feed> = listOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): FeedViewHolder {
    return FeedViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_feed, parent, false
        )
    )
  }

  fun setItems(items: List<Feed>) {
    feedItemsList = items
  }

  override fun getItemCount(): Int {
    return feedItemsList.size
  }

  override fun onBindViewHolder(
    holder: FeedViewHolder,
    position: Int
  ) {
    holder.bind(viewModel = viewModel, position = position)
  }

  class FeedViewHolder(private val binding: ItemFeedBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(
      viewModel: FeedViewModel,
      position: Int
    ) {
      val feed = viewModel.feedListLiveData.value?.get(position)
      feed?.let {
        binding.feed = feed
        val action = feed.payload.action + " <b>" + feed.repo.name + "</b>"
        HtmlCompat.fromHtml(action, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvAction.text = HtmlCompat.fromHtml(action, HtmlCompat.FROM_HTML_MODE_LEGACY)
      }
    }
  }
}