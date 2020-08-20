package com.gitsurfer.gitsurf.ui.main.repo.files

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.R.layout
import com.gitsurfer.gitsurf.data.network.models.response.RepoFile
import com.gitsurfer.gitsurf.data.persistence.models.RoomFeed
import com.gitsurfer.gitsurf.databinding.FragmentFilesBinding
import com.gitsurfer.gitsurf.ui.base.BaseFragment
import com.gitsurfer.gitsurf.ui.main.repo.files.adapter.FilesAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FilesFragment(
  private val repoName: String?,
  private val branch: String?,
  private val owner: String?
) :
  BaseFragment<FilesViewModel, FragmentFilesBinding>(layout.fragment_files) {

  override val viewModel: FilesViewModel by viewModels()
  override fun getViewBinding(view: View) = FragmentFilesBinding.bind(view)

  val adapter = FilesAdapter()

  override fun init() {
    initRecyclerView()
    viewModel.fetchRepoFiles(owner, repoName, branch)
    setListeners()
    listen()
  }

  private fun listen() {
    viewModel.repoFileLiveData
      .observe(this, Observer { repoFilesList ->
        Timber.d("Size: %s", repoFilesList.size)
        val sortedList = repoFilesList.sortedWith(compareBy { it.size })
        updateAdapter(sortedList)
      })
  }

  private fun setListeners() {
    binding.swipeContainer.setOnRefreshListener {
      viewModel.refresh()
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
}