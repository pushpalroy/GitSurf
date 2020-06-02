package com.gitsurfer.gitsurf.model.network.models.response

import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomPayload
import com.google.gson.annotations.SerializedName

data class Payload(
  @SerializedName("action") val action: String,
  @SerializedName("member") val member: Member?
)

fun Payload.toRoomPayload() = RoomPayload(
    action = action,
    member = member?.toRoomMember()
)