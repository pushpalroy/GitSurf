package com.gitsurfer.gitsurf.ui.code

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.gitsurfer.gitsurf.databinding.ActivityCodeBinding
import com.gitsurfer.gitsurf.ui.base.BaseActivity
import com.gitsurfer.gitsurf.utils.constants.FILE_NAME
import com.gitsurfer.gitsurf.utils.constants.FILE_URL
import com.gitsurfer.mdview.ContentChangeListener
import com.gitsurfer.mdview.MdView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_code.toolbar
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class CodeActivity : BaseActivity<CodeViewModel, ActivityCodeBinding>(), ContentChangeListener {

  override val viewModel: CodeViewModel by viewModels()
  override fun getViewBinding() = ActivityCodeBinding.inflate(layoutInflater)

  companion object {
    @JvmStatic
    fun getBundle(
      fileUrl: String,
      fileName: String
    ): Bundle {
      return Bundle().apply {
        putString(FILE_URL, fileUrl)
        putString(FILE_NAME, fileName)
      }
    }
  }

  override fun init() {
    setUpToolbar()
    initMarkDown()
    fetchCode()
    listen()
  }

  private fun listen() {
    viewModel.codeSrc.observe(this, Observer { codeSrc ->
      try {
        binding.markdownView.setMarkDownText(codeSrc.string())
        hideLoader()
      } catch (ex: IOException) {
        Timber.tag(MdView.TAG)
          .e(ex)
      }
    })
  }

  private fun initMarkDown() {
    binding.markdownView.isOpenUrlInBrowser = true
    binding.markdownView.setEnableNestedScrolling(true)
    binding.markdownView.setInterceptTouch(true)
    binding.markdownView.setOnContentChangedListener(this)
  }

  private fun setUpToolbar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeButtonEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = intent.getStringExtra(FILE_NAME)
  }

  private fun fetchCode() {
    showLoader()
    viewModel.fetchCode(
      intent.getStringExtra(FILE_URL)
    )
  }

  override fun onContentChanged(progress: Int) {
    Timber.d("OnContentChanged: %s", progress)
  }

  override fun onScrollChanged(
    reachedTop: Boolean,
    scroll: Int
  ) {
    Timber.d("onScrollChanged: %s, reachedTop: %s", scroll, reachedTop)
  }

  private fun showLoader() {
    binding.pbLoader.visibility = View.VISIBLE
  }

  private fun hideLoader() {
    binding.pbLoader.visibility = View.GONE
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}