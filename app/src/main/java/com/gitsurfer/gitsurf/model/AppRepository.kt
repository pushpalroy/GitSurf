package com.gitsurfer.gitsurf.model

import com.gitsurfer.gitsurf.model.network.NetworkDataProvider
import com.gitsurfer.gitsurf.model.network.models.request.AuthRequestModel
import com.gitsurfer.gitsurf.model.roomdatabase.LocalDataProvider

class AppRepository(
  private val networkDataProvider: NetworkDataProvider,
  private val localDataProvider: LocalDataProvider
) {

  suspend fun getBasicToken(
    credential: String,
    authRequestModel: AuthRequestModel
  ) = networkDataProvider.getBasicToken(
      credential = credential,
      authRequestModel = authRequestModel
  )

  suspend fun getUserInfo(
    authToken: String
  ) = networkDataProvider.getUserInfo(
      authToken = authToken
  )

  suspend fun getReceivedFeeds(
    authToken: String?,
    user: String?
  ) = networkDataProvider.getReceivedFeeds(
      authToken = authToken,
      user = user
  )

  suspend fun getAccessToken(
    clientId: String,
    clientSecret: String,
    code: String,
    state: String
  ) = networkDataProvider.getAccessToken(
      clientId = clientId,
      clientSecret = clientSecret,
      code = code,
      state = state
  )

  suspend fun getUserLocal(
    accessToken: String
  ) = localDataProvider.getUser(
      accessToken = accessToken
  )
}