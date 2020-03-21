package com.gitsurfer.gitsurf.model.network

import com.gitsurfer.gitsurf.model.network.api.RetrofitApi
import com.gitsurfer.gitsurf.model.network.models.AuthRequestModel
import com.gitsurfer.gitsurf.utils.networkCall

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