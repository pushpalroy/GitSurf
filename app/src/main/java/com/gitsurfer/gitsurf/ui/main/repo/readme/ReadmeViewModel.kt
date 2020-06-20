package com.gitsurfer.gitsurf.ui.main.repo.readme

import androidx.hilt.lifecycle.ViewModelInject
import com.gitsurfer.gitsurf.data.AppRepository
import com.gitsurfer.gitsurf.ui.base.BaseViewModel

class ReadmeViewModel @ViewModelInject constructor(
  val appRepository: AppRepository
) : BaseViewModel() {

}