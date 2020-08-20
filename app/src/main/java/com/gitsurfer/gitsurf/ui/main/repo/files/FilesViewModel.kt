package com.gitsurfer.gitsurf.ui.main.repo.files

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkManager
import com.gitsurfer.gitsurf.data.network.models.response.RepoFile
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FilesViewModel @ViewModelInject constructor(
  var appRepository: AppRepository,
  var networkManager: NetworkManager
) : BaseViewModel() {

  private val _repoFilesLiveData = MutableLiveData<List<RepoFile>>()
  val repoFileLiveData: LiveData<List<RepoFile>>
    get() = _repoFilesLiveData

  fun fetchRepoFiles(
    owner: String?,
    repoName: String?,
    branch: String?
  ) {
    owner?.let {
      repoName?.let {
        branch?.let {
          viewModelScope.launch {
            when {
              networkManager.hasInternetAccess() -> {
                val repoFiles = appRepository
                  .getRepoFiles(
                    owner = owner,
                    repoName = repoName.split("/")[1],
                    branch = branch
                  )
                repoFiles.first?.let { repoFilesList ->
                  _repoFilesLiveData.postValue(repoFilesList)
                }
                repoFiles.second?.let {
                  updateExceptionLiveData(it)
                }
              }
            }
          }
        }
      }
    }
  }

  fun refresh() {

  }
}