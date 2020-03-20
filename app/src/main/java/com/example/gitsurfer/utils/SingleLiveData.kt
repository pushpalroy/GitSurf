/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.gitsurfer.utils

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveData<T> : MutableLiveData<T>() {

  private val mPending = AtomicBoolean(false)

  @MainThread
  fun observeSingleLiveData(
    owner: LifecycleOwner,
    observer: Observer<T>
  ) {

    if (hasActiveObservers()) {
      Timber.w("Multiple observers registered but only one will be notified of changes.")
    }

    // Observe the internal MutableLiveData
    super.observe(owner, Observer<T> { t ->
      if (mPending.compareAndSet(true, false)) {
        observer.onChanged(t)
      }
    })
  }

  @MainThread
  fun setLiveDataValue(@Nullable t: T?) {
    mPending.set(true)
    super.setValue(t)
  }

  private fun postLiveDataValue(@Nullable t: T?) {
    mPending.set(true)
    super.postValue(t)
  }

  @MainThread
  fun call(t: T? = null) {
    postLiveDataValue(t)
  }

  companion object {
    private val TAG = "SingleLiveData"
  }
}