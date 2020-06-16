package com.gitsurfer.gitsurf.data

import com.gitsurfer.gitsurf.data.network.NetworkDataProvider
import com.gitsurfer.gitsurf.data.network.models.request.AuthRequestModel
import com.gitsurfer.gitsurf.data.persistence.LocalDataProvider
import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.data.persistence.models.RoomUser

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
    user: String?,
    page: Int?,
    perPage: Int?
  ) = networkDataProvider.getReceivedFeeds(
      authToken = authToken,
      user = user,
      page = page,
      perPage = perPage
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

  suspend fun insertRoomUser(
    roomUser: RoomUser
  ) = localDataProvider.insertUser(
      roomUser = roomUser
  )

  suspend fun getRoomUser(
    login: String
  ): RoomUser? = localDataProvider.getUser(
      login = login
  )

  suspend fun insertRoomFeed(
    roomFeed: RoomFeed
  ) = localDataProvider.insertFeed(
      roomFeed = roomFeed
  )

  fun getRoomFeeds() = localDataProvider.getAllFeeds()

  suspend fun removeRoomFeed(roomFeed: RoomFeed) =
    localDataProvider.deleteFeed(roomFeed)
}