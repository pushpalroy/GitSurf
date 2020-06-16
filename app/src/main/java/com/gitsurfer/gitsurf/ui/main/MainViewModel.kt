package com.gitsurfer.gitsurf.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.data.network.NetworkManager
import com.gitsurfer.gitsurf.data.persistence.models.RoomUser
import com.gitsurfer.gitsurf.data.utils.SharedPrefUtils
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
  private val appRepository: AppRepository,
  private val networkManager: NetworkManager,
  private val prefUtils: SharedPrefUtils
) : BaseViewModel() {

  private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
  val isAuthorizedLiveData: LiveData<Boolean>
    get() = _isAuthorizedLiveData

  private val _roomUserLiveData = MutableLiveData<RoomUser>()
  val roomUserLiveData: LiveData<RoomUser>
    get() = _roomUserLiveData

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

  fun getLocalUserDetails() {
    viewModelScope.launch {
      prefUtils.userName?.let { userName ->
        val roomUser = appRepository.getRoomUser(login = userName)

        roomUser?.let {
          _roomUserLiveData.value = roomUser
        }
      }
    }
  }
}