package com.gitsurfer.gitsurf.model.roomdatabase.converter

import androidx.room.TypeConverter
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomActor
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomPayload
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomRepo
import com.google.gson.Gson
import java.util.Date

class RoomTypeConverters {

  @TypeConverter
  fun fromRoomActor(roomActor: RoomActor?): String? = Gson().toJson(roomActor)

  @TypeConverter
  fun toRoomActor(actorString: String?): RoomActor? {
    return when {
      !actorString.isNullOrEmpty() -> {
        Gson().fromJson(actorString, RoomActor::class.java)
      }
      else -> null
    }
  }

  @TypeConverter
  fun fromRoomRepo(roomRepo: RoomRepo?): String? = Gson().toJson(roomRepo)

  @TypeConverter
  fun toRoomRoom(repoString: String?): RoomRepo? {
    return when {
      !repoString.isNullOrEmpty() -> {
        Gson().fromJson(repoString, RoomRepo::class.java)
      }
      else -> null
    }
  }

  @TypeConverter
  fun fromPayload(roomPayload: RoomPayload?): String? = Gson().toJson(roomPayload)

  @TypeConverter
  fun toPayload(repoString: String?): RoomPayload? {
    return when {
      !repoString.isNullOrEmpty() -> {
        Gson().fromJson(repoString, RoomPayload::class.java)
      }
      else -> null
    }
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time
  }

  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }
}