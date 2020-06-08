package com.gitsurfer.gitsurf.ui.main.repo

import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import javax.inject.Inject

class RepoViewModel @Inject constructor(
  val appRepository: AppRepository
) : BaseViewModel()