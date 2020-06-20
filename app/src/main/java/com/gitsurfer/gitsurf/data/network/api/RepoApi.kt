package com.gitsurfer.gitsurf.data.network.api

import com.gitsurfer.gitsurf.data.network.models.response.Repo
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url

interface RepoApi {

  /**
   * Get repository details
   * @param authToken
   */
  @GET("/repos/{owner}/{repoName}")
  @Headers("Accept: application/json")
  suspend fun getRepoDetails(
    @Header("Authorization") authToken: String,
    @Path("owner") owner: String?,
    @Path("repoName") repoName: String?
  ): Response<Repo>

  @GET
  @Headers("Accept: application/vnd.github.html")
  suspend fun getFileAsHtmlStream(
    @Header("forceNetWork") forceNetWork: Boolean,
    @Url url: String?
  ): Response<ResponseBody>
}