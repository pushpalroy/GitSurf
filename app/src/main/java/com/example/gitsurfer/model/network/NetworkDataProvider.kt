package com.example.gitsurfer.model.network

import com.example.gitsurfer.model.network.api.RetrofitApi
import com.example.gitsurfer.model.network.models.AuthRequestModel
import com.example.gitsurfer.utils.networkCall

class NetworkDataProvider(
  private val retrofitApi: RetrofitApi
) {

  suspend fun authorizations(authRequestModel: AuthRequestModel) = networkCall {
    retrofitApi.authorizations(authRequestModel)
  }

  suspend fun getAccessToken(
    clientId: String,
    clientSecret: String,
    code: String,
    state: String
  ) = networkCall {
    retrofitApi.getAccessToken(clientId, clientSecret, code, state)
  }
}