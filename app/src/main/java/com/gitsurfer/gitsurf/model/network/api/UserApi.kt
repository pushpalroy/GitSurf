package com.gitsurfer.gitsurf.model.network.api

import com.gitsurfer.gitsurf.model.network.models.response.Feed
import com.gitsurfer.gitsurf.model.network.models.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {

  /**
   * Lists public and private profile information when authenticated
   * through basic auth or OAuth with the user scope.
   * @param authToken
   */
  @GET("/user")
  suspend fun getUserInformation(
    @Header("Authorization") authToken: String
  ): Response<User>

  @GET("/users/{user}/received_events")
  suspend fun getReceivedFeeds(
    @Header("Authorization") authToken: String?,
    @Path("user") user: String?
  ): Response<List<Feed>>
}