package com.gitsurfer.gitsurf.model.network

import com.gitsurfer.gitsurf.model.network.api.LoginApi
import com.gitsurfer.gitsurf.model.network.api.UserApi
import com.gitsurfer.gitsurf.model.network.models.AuthRequestModel
import com.gitsurfer.gitsurf.utils.networkCall

class NetworkDataProvider(
  private val loginApi: LoginApi,
  private val userApi: UserApi
) {

  suspend fun getBasicToken(
    credential: String,
    authRequestModel: AuthRequestModel
  ) = networkCall {
    loginApi.getBasicToken(credential, authRequestModel)
  }

  suspend fun getUserInfo(
    authToken: String
  ) = networkCall {
    userApi.getUserInformation(authToken)
  }

  suspend fun getAccessToken(
    clientId: String,
    clientSecret: String,
    code: String,
    state: String
  ) = networkCall {
    loginApi.getAccessToken(clientId, clientSecret, code, state)
  }
}