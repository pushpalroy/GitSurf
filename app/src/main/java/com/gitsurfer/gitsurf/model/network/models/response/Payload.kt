package com.gitsurfer.gitsurf.model.network.models.response

import com.google.gson.annotations.SerializedName

data class Payload(
  @SerializedName("action") val action: String
)