package com.gitsurfer.gitsurf.data.network.models.response

import com.gitsurfer.gitsurf.data.roomdatabase.models.RoomPayload
import com.squareup.moshi.Json

data class Payload(
  @Json(name = "action") val action: String?,
  @Json(name = "member") val member: Member?
)

fun Payload.toRoomPayload() = RoomPayload(
    action = action,
    member = member?.toRoomMember()
)