package com.gitsurfer.gitsurf.ui.main.repo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkManager
import com.gitsurfer.gitsurf.data.network.models.response.Feed
import com.gitsurfer.gitsurf.data.network.models.response.Repo
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RepoViewModel @ViewModelInject constructor(
  private val appRepository: AppRepository,
  private val networkManager: NetworkManager
) : BaseViewModel() {

  private val _repoLiveData = MutableLiveData<Repo>()
  val repoLiveData: LiveData<Repo>
    get() = _repoLiveData

  fun fetchRepoDetails(repoName: String) {
      viewModelScope.launch {
        when {
          networkManager.hasInternetAccess() -> {
            val repoDetails = appRepository.getRepoDetails(
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