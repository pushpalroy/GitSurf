package com.gitsurfer.gitsurf.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>(layoutId: Int) :
  Fragment(layoutId) {

  protected abstract val viewModel: VM
  protected lateinit var binding: VB

  abstract fun getViewBinding(view: View): VB
  abstract fun init()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = getViewBinding(view)
    init()
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