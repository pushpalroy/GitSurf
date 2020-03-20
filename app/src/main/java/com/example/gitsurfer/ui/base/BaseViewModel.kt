package com.example.gitsurfer.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitsurfer.utils.SingleLiveData

abstract class BaseViewModel : ViewModel() {

  val progressLiveData = SingleLiveData<Boolean>()
  val exceptionLiveData = MutableLiveData<Exception>()

  internal fun updateLiveDataProgress(progress: Boolean) {
    progressLiveData.call(progress)
  }

  internal fun updateLiveDataException(second: Exception?) {
    exceptionLiveData.postValue(second)
  }
}