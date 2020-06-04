package com.gitsurfer.gitsurf.utils

import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomFeed

object GithubUtil {

  private const val EVENT_WATCH = "WatchEvent"
  private const val EVENT_FORK = "ForkEvent"
  private const val EVENT_CREATE = "CreateEvent"
  private const val EVENT_MEMBER = "MemberEvent"

  private const val ACTION_STARRED = "starred"
  private const val ACTION_FORKED = "forked"
  private const val ACTION_CREATED = "created"
  private const val ACTION_STARTED = "started"
  private const val ACTION_ADDED = "added"

  @JvmStatic
  fun getActionFromEventType(eventType: String?): String {
    eventType?.let {
      return when (eventType) {
        EVENT_WATCH -> ACTION_STARRED
        EVENT_FORK -> ACTION_FORKED
        EVENT_CREATE -> ACTION_CREATED
        EVENT_MEMBER -> ACTION_ADDED
        else -> ACTION_STARTED
      }
    }
    return ACTION_STARTED
  }

  @JvmStatic
  fun getIconFromEventType(eventType: String?): Int {
    eventType?.let {
      return when (eventType) {
        EVENT_WATCH -> R.drawable.ic_star
        EVENT_FORK -> R.drawable.ic_fork
        EVENT_CREATE -> R.drawable.ic_created
        EVENT_MEMBER -> R.drawable.ic_add
        else -> R.drawable.ic_star
      }
    }
    return R.drawable.ic_star
  }

  @JvmStatic
  fun getDescriptionFromAction(
    action: String?,
    feed: Feed
  ): String {
    action?.let {
      if (action == ACTION_ADDED) {
        return " " + feed.payload?.member?.login + " as a collaborator to "
      }
    }
    return ""
  }

  @JvmStatic
  fun getDescriptionFromAction(
    action: String?,
    roomFeed: RoomFeed
  ): String {
    action?.let {
      if (action == ACTION_ADDED) {
        return " " + roomFeed.payload?.member?.login + " as a collaborator to "
      }
    }
    return ""
  }
}