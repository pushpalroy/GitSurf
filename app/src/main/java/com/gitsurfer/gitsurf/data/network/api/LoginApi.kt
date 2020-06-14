package com.gitsurfer.gitsurf.data.network.api

import com.gitsurfer.gitsurf.data.network.models.request.AuthRequestModel
import com.gitsurfer.gitsurf.data.network.models.response.BasicToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {

  /**
   * Returns basic authorization token
   * @param authRequestModel
   */
  @POST("/authorizations")
  @Headers("Accept: application/json")
  suspend fun getBasicToken(
    @Header("Authorization") credential: String,
    @Body authRequestModel: AuthRequestModel
  ): Response<BasicToken>

  /**
   * Returns basic token
   */
  @POST("/login/oauth/access_token")
  @Headers("Accept: application/json")
  suspend fun getAccessToken(
    @Query("client_id") clientId: String,
    @Query("client_secret") clientSecret: String,
    @Query("code") code: String,
    @Query("state") state: String
  ): Response<BasicToken>
}