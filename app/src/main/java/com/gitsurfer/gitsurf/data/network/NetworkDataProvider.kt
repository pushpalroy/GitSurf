package com.gitsurfer.gitsurf.data.network

import com.gitsurfer.gitsurf.data.network.api.LoginApi
import com.gitsurfer.gitsurf.data.network.api.RepoApi
import com.gitsurfer.gitsurf.data.network.api.UserApi
import com.gitsurfer.gitsurf.data.network.models.request.AuthRequestModel
import com.gitsurfer.gitsurf.data.utils.networkCall
import java.nio.file.Path

class NetworkDataProvider(
  private val loginApi: LoginApi,
  private val userApi: UserApi,
  private val repoApi: RepoApi
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

  suspend fun getReceivedFeeds(
    authToken: String?,
    user: String?,
    page: Int?,
    perPage: Int?
  ) = networkCall {
    userApi.getReceivedFeeds(authToken, user, page, perPage)
  }

  suspend fun getAccessToken(
    clientId: String,
    clientSecret: String,
    code: String,
    state: String
  ) = networkCall {
    loginApi.getAccessToken(clientId, clientSecret, code, state)
  }

  suspend fun getRepoDetails(
    authToken: String,
    owner: String,
    repoName: String
  ) = networkCall {
    repoApi.getRepoDetails(authToken, owner, repoName)
  }

  suspend fun getFileAsHtmlStream(url: String) = networkCall {
    repoApi.getFileAsHtmlStream(url)
  }

  suspend fun getRepoFiles(
    owner: String,
    repoName: String,
    branch: String
  ) = networkCall {
    repoApi.getRepoFiles(owner, repoName, branch)
  }
}