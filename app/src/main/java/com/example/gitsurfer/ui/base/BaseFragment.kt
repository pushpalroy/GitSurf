package com.example.gitsurfer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.gitsurfer.BR
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {

  lateinit var binding: B
  lateinit var viewModel: VM

  abstract fun getViewModelClass(): Class<VM>
  abstract fun getLayoutId(): Int
  abstract fun getViewModelFactoryInstance(): ViewModelProvider.Factory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(this).get(getViewModelClass())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    binding.setVariable(BR.viewModel, viewModel)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onDetach() {
    super.onDetach()
  }
}

fun BaseFragment<*, *>.showSnackBarMessage(
  message: String?,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  this.binding.root.let {
    message?.let { it1 ->
      Snackbar.make(it, it1, duration)
          .show()
    }
  }
}