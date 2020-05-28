package com.gitsurfer.gitsurf.utils

object DateUtil {

  private const val SECOND_MILLIS = 1000
  private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
  private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
  private const val DAY_MILLIS = 24 * HOUR_MILLIS

  @JvmStatic
  fun getTimeAgo(inputTime: Long): String? {
    var time = inputTime

    if (time < 1000000000000L) {
      time *= 1000
    }

    val now = System.currentTimeMillis()
    if (time > now || time <= 0) {
      return null
    }

    val diff = now - time;
    return when {
      diff < MINUTE_MILLIS -> {
        "Just now"
      }
      diff < 2 * MINUTE_MILLIS -> {
        "A minute ago"
      }
      diff < 50 * MINUTE_MILLIS -> {
        (diff / MINUTE_MILLIS).toString() + " minutes ago"
      }
      diff < 90 * MINUTE_MILLIS -> {
        "An hour ago"
      }
      diff < 24 * HOUR_MILLIS -> {
        (diff / HOUR_MILLIS).toString() + " hours ago"
      }
      diff < 48 * HOUR_MILLIS -> {
        "Yesterday";
      }
      else -> {
        (diff / DAY_MILLIS).toString() + " days ago";
      }
    }
  }
}