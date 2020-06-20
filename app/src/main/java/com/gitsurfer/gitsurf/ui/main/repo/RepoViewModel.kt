package com.gitsurfer.gitsurf.ui.main.repo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkManager
import com.gitsurfer.gitsurf.data.network.models.response.Repo
import com.gitsurfer.gitsurf.data.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import com.gitsurfer.gitsurf.utils.TOKEN_PREFIX
import kotlinx.coroutines.launch

class RepoViewModel @ViewModelInject constructor(
  val appRepository: AppRepository,
  private val networkManager: NetworkManager,
  private val prefUtils: SharedPrefUtils
) : BaseViewModel() {

  private val _repoLiveData = MutableLiveData<Repo>()
  val repoLiveData: LiveData<Repo>
    get() = _repoLiveData

  fun fetchRepoDetails(
    owner: String?,
    repoName: String?
  ) {
    owner?.let {
      repoName?.let {
        viewModelScope.launch {
          when {
            networkManager.hasInternetAccess() -> {
              val repoDetails = appRepository.getRepoDetails(
                authToken = TOKEN_PREFIX + prefUtils.authToken,
                owner = repoName.split("/")[0],
                repoName = repoName.split("/")[1]
              )
              repoDetails.first?.let { repo ->
                _repoLiveData.postValue(repo)
              }
              repoDetails.second?.let {
                updateExceptionLiveData(it)
              }
            }
          }
        }
      }
    }
  }
}