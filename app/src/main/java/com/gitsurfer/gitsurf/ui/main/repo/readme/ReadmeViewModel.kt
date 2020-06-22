package com.gitsurfer.gitsurf.ui.main.repo.readme

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkManager
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.IOException

class ReadmeViewModel @ViewModelInject constructor(
  val appRepository: AppRepository,
  val networkManager: NetworkManager
) : BaseViewModel() {

  private val _readMeSrc = MutableLiveData<ResponseBody>()
  val readMeSrc: LiveData<ResponseBody>
    get() = _readMeSrc

  companion object {
    private const val TAG = "ReadMe"
  }

  fun getReadMeSource(url: String) {
    viewModelScope.launch {
      when {
        networkManager.hasInternetAccess() -> {
          val response = appRepository.getFileAsHtmlStream(url)

          response.first?.let {
            try {
              _readMeSrc.postValue(it)
            } catch (ex: IOException) {
              Timber.tag(TAG).e(ex)
            }
          }

          response.second?.let {
            Timber.tag(TAG).e(it)
          }
        }
      }
    }
  }
}