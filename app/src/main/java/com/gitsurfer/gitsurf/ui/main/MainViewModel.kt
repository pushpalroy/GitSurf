package com.gitsurfer.gitsurf.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.model.network.NetworkManager
import com.gitsurfer.gitsurf.model.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val appRepository: AppRepository,
  private val networkManager: NetworkManager,
  private val prefUtils: SharedPrefUtils
) : BaseViewModel() {

  private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
  val isAuthorizedLiveData: LiveData<Boolean>
    get() = _isAuthorizedLiveData

  fun setAuthorizedFromPref() {
    _isAuthorizedLiveData.value = prefUtils.authToken != null
  }

  fun setAuthorized(isAuthorized: Boolean) {
    if (!isAuthorized) {
      prefUtils.userName = null
      prefUtils.authToken = null
    }
    _isAuthorizedLiveData.value = isAuthorized
  }
}