package com.gitsurfer.gitsurf.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.gitsurfer.gitsurf.BR
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel, AVM : ViewModel> :
    DaggerFragment() {

  lateinit var binding: B
  lateinit var viewModel: VM
  lateinit var activityViewModel: AVM

  abstract fun getViewModelClass(): Class<VM>
  abstract fun getLayoutId(): Int

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    bindContentView()
    return binding.root
  }

  private fun bindContentView() {
    viewModel = ViewModelProvider(this, viewModelFactory)
        .get(getViewModelClass())
    activityViewModel = ViewModelProvider(getActivityViewModelOwner(), viewModelFactory)
        .get(getActivityViewModelClass())
    binding.setVariable(BR.viewModel, viewModel)
  }

  abstract fun getActivityViewModelClass(): Class<AVM>
  abstract fun getActivityViewModelOwner(): ViewModelStoreOwner

  fun showSnackBar(text: CharSequence?) {
    text?.let {
      Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT)
          .show()
    }
  }

  fun showToast(
    text: CharSequence?,
    length: Int = Toast.LENGTH_SHORT
  ) {
    text?.let {
      Toast.makeText(requireContext().applicationContext, text, length)
          .show()
    }
  }
}