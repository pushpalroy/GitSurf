package com.gitsurfer.gitsurf.model

import com.gitsurfer.gitsurf.model.network.NetworkDataProvider
import com.gitsurfer.gitsurf.model.network.models.AuthRequestModel
import com.gitsurfer.gitsurf.model.roomdatabase.LocalDataProvider

class AppRepository(
  private val networkDataProvider: NetworkDataProvider,
  private val localDataProvider: LocalDataProvider
) {

  private suspend fun loginUserAsyncInternal(
    authRequestModel: AuthRequestModel
  ) = networkDataProvider.authorizations(
      authRequestModel = authRequestModel
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