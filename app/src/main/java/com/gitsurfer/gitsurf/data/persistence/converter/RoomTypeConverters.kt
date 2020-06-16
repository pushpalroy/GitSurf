package com.gitsurfer.gitsurf.data.persistence.converter

import androidx.room.TypeConverter
import com.gitsurfer.gitsurf.data.persistence.models.RoomActor
import com.gitsurfer.gitsurf.data.persistence.models.RoomPayload
import com.gitsurfer.gitsurf.data.persistence.models.RoomRepo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

class RoomTypeConverters {

  @TypeConverter
  fun fromRoomActor(roomActor: RoomActor?): String? =
    getMoshiBuilder()
        .build()
        .adapter(RoomActor::class.java)
        .toJson(roomActor)

  @TypeConverter
  fun toRoomActor(actorString: String?): RoomActor? {
    return when {
      !actorString.isNullOrEmpty() -> {
        getMoshiBuilder()
            .build()
            .adapter(RoomActor::class.java)
            .fromJson(actorString)
      }
      else -> null
    }
  }

  @TypeConverter
  fun fromRoomRepo(roomRepo: RoomRepo?): String? =
    getMoshiBuilder()
        .build()
        .adapter(RoomRepo::class.java)
        .toJson(roomRepo)

  @TypeConverter
  fun toRoomRoom(repoString: String?): RoomRepo? {
    return when {
      !repoString.isNullOrEmpty() -> {
        getMoshiBuilder()
            .build()
            .adapter(RoomRepo::class.java)
            .fromJson(repoString)
      }
      else -> null
    }
  }

  @TypeConverter
  fun fromPayload(roomPayload: RoomPayload?): String? =
    getMoshiBuilder()
        .build()
        .adapter(RoomPayload::class.java)
        .toJson(roomPayload)

  @TypeConverter
  fun toPayload(payloadString: String?): RoomPayload? {
    return when {
      !payloadString.isNullOrEmpty() -> {
        getMoshiBuilder()
            .build()
            .adapter(RoomPayload::class.java)
            .fromJson(payloadString)
      }
      else -> null
    }
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): String? {
    return getJsonDateAdapter()
        .toJson(date)
  }

  @TypeConverter
  fun fromTimestamp(value: String?): Date? {
    return value?.let {
      getJsonDateAdapter().fromJson(value)
    }
  }

  private fun getMoshiBuilder(): Moshi.Builder {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
  }

  private fun getJsonDateAdapter(): JsonAdapter<Date> {
    return getMoshiBuilder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
        .adapter(Date::class.java)
  }
}