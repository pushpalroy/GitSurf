package com.gitsurfer.gitsurf.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gitsurfer.gitsurf.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

  private var compositeDisposable: CompositeDisposable? = null
  val progressLiveData = SingleLiveData<Boolean>()

  private val _exceptionLiveData = MutableLiveData<Exception>()
  val exceptionLiveData: LiveData<Exception>
    get() = _exceptionLiveData

  internal fun updateProgressLiveData(progress: Boolean) {
    progressLiveData.call(progress)
  }

  internal fun updateExceptionLiveData(exception: Exception?) {
    _exceptionLiveData.postValue(exception)
  }

  override fun onCleared() {
    super.onCleared()
    if (compositeDisposable != null) {
      compositeDisposable!!.dispose()
      compositeDisposable!!.clear()
      compositeDisposable = null
    }
  }

  protected fun addDisposable(disposable: Disposable) {
    if (compositeDisposable == null) {
      compositeDisposable = CompositeDisposable()
    }
    compositeDisposable!!.add(disposable)

  }
}