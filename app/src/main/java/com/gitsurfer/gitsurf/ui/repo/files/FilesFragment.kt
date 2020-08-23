package com.gitsurfer.gitsurf.ui.repo.files

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.data.network.models.response.RepoFile
import com.gitsurfer.gitsurf.databinding.FragmentFilesBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.code.CodeActivity
import com.gitsurfer.gitsurf.ui.repo.files.adapter.FileClickListener
import com.gitsurfer.gitsurf.ui.repo.files.adapter.FilesAdapter
import com.gitsurfer.gitsurf.utils.GithubUtil.TYPE_DIR
import com.gitsurfer.gitsurf.utils.GithubUtil.TYPE_FILE
import com.gitsurfer.gitsurf.utils.GithubUtil.getPreviousDirPath
import com.gitsurfer.gitsurf.utils.ui.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FilesFragment(
  private val repoName: String?,
  private val branch: String?,
  private val owner: String?
) :
  BaseFragment<FilesViewModel, FragmentFilesBinding>(layout.fragment_files),
  FileClickListener {

  override val viewModel: FilesViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentFilesBinding.bind(view)

  private var currentPath: String = ""
  val adapter = FilesAdapter(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activity?.onBackPressedDispatcher?.addCallback(this,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          if (currentPath.isNotEmpty()) {
            navToPrevDir()
          } else {
            isEnabled = false
            activity?.onBackPressed()
          }
        }
      })
  }

  override fun init() {
    initRecyclerView()
    fetchRepoFiles()
    setListeners()
    listen()
  }

  private fun listen() {
    viewModel.repoFileLiveData
      .observe(this, Observer { repoFilesList ->
        Timber.d("Size: %s", repoFilesList.size)
        updateAdapter(repoFilesList)
        hideLoader()
      })
  }

  private fun setListeners() {
    binding.swipeContainer.setOnRefreshListener {
      fetchRepoFiles()
    }
  }

  private fun initRecyclerView() {
    binding.rvFiles.layoutManager = LinearLayoutManager(activity)
    binding.rvFiles.adapter = adapter
  }

  private fun updateAdapter(items: List<RepoFile?>) {
    adapter.setItems(items)
    adapter.notifyDataSetChanged()
  }

  override fun onFileClicked(position: Int) {
    val repoFile = viewModel.repoFileLiveData.value?.get(position)
    repoFile?.let { file ->
      if (file.type == TYPE_DIR) {
        currentPath = file.path
        fetchRepoFiles()
      } else if (file.type == TYPE_FILE) {
        navToCodeViewer(file)
      }
    }
  }

  private fun navToCodeViewer(file: RepoFile) {
    file.url?.let { fileUrl ->
      AppNavigator.startActivityWithData(
        CodeActivity::class.java,
        CodeActivity.getBundle(fileName = file.fileName, fileUrl = fileUrl),
        requireActivity() as AppCompatActivity,
        false
      )
    }
  }

  private fun navToPrevDir() {
    currentPath = getPreviousDirPath(currentPath)
    fetchRepoFiles()
  }

  private fun fetchRepoFiles() {
    showLoader()
    viewModel.fetchRepoFiles(owner, repoName, currentPath, branch)
  }

  private fun showLoader() {
    binding.swipeContainer.isRefreshing = true
  }

  private fun hideLoader() {
    binding.swipeContainer.isRefreshing = false
  }
}