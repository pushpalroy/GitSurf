package com.gitsurfer.gitsurf.model.network.api

import com.gitsurfer.gitsurf.model.network.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

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
}