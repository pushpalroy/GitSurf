package com.gitsurfer.gitsurf.utils

object GithubUtil {

  private const val EVENT_WATCH = "WatchEvent"
  private const val EVENT_FORK = "ForkEvent"
  private const val EVENT_CREATE = "CreateEvent"

  private const val ACTION_STARRED = "starred"
  private const val ACTION_FORKED = "forked"
  private const val ACTION_CREATED = "created"
  private const val ACTION_STARTED = "started"

  @JvmStatic
  fun getActionFromEventType(eventType: String?): String {
    eventType?.let {
      return when (eventType) {
        EVENT_WATCH -> ACTION_STARRED
        EVENT_FORK -> ACTION_FORKED
        EVENT_CREATE -> ACTION_CREATED
        else -> ACTION_STARTED
      }
    }
    return ACTION_STARTED
  }
}