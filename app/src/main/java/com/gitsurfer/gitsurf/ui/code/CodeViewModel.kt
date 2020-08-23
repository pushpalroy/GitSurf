package com.gitsurfer.gitsurf.ui.code

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

class CodeViewModel @ViewModelInject constructor(
  var appRepository: AppRepository,
  var networkManager: NetworkManager
) : BaseViewModel() {

  private val _codeSrc = MutableLiveData<ResponseBody>()
  val codeSrc: LiveData<ResponseBody>
    get() = _codeSrc

  fun fetchCode(url: String?) {
    url?.let {
      viewModelScope.launch {
        when {
          networkManager.hasInternetAccess() -> {
            val response = appRepository.getFileAsHtmlStream(it)

            response.first?.let {
              try {
                _codeSrc.postValue(it)
              } catch (ex: IOException) {
                Timber.e(ex)
              }
            }

            response.second?.let {
              Timber.e(it)
            }
          }
        }
      }
    }
  }
}