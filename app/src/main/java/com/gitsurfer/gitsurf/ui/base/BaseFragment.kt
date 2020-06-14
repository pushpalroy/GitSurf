package com.gitsurfer.gitsurf.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<B : ViewDataBinding> :
  Fragment() {

  lateinit var binding: B
  abstract fun getLayoutId(): Int

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    return binding.root
  }

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