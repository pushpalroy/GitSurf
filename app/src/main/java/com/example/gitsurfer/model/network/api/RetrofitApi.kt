package com.example.gitsurfer.model.network.api

import com.example.gitsurfer.model.network.models.AuthRequestModel
import com.example.gitsurfer.model.network.models.BasicToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApi {

  @POST("authorizations")
  @Headers("Accept: application/json")
  suspend fun authorizations(
    @Body authRequestModel: AuthRequestModel
  ): Response<BasicToken>

  @POST("login/oauth/access_token")
  @Headers("Accept: application/json")
  suspend fun getAccessToken(
    @Query("client_id") clientId: String,
    @Query("client_secret") clientSecret: String,
    @Query("code") code: String,
    @Query("state") state: String
  ): Response<BasicToken>
}