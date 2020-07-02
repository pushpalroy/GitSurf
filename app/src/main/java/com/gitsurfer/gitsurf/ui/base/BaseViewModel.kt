package com.gitsurfer.gitsurf.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gitsurfer.gitsurf.utils.ui.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

  private var compositeDisposable: CompositeDisposable? = null

  private val _progressLiveData = SingleLiveEvent<Boolean>()
  val progressLiveData: SingleLiveEvent<Boolean>
    get() = _progressLiveData

  private val _exceptionLiveData = MutableLiveData<Exception>()
  val exceptionLiveData: LiveData<Exception>
    get() = _exceptionLiveData

  internal fun updateProgressLiveData(progress: Boolean) {
    _progressLiveData.postValue(progress)
  }

  internal fun updateExceptionLiveData(exception: Exception?) {
    _exceptionLiveData.postValue(exception)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable?.dispose()
    compositeDisposable?.clear()
  }

  protected fun addDisposable(disposable: Disposable) {
    if (compositeDisposable == null) {
      compositeDisposable = CompositeDisposable()
    }
    compositeDisposable?.add(disposable)
  }
}