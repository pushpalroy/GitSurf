package com.gitsurfer.gitsurf.ui.main.repo.readme

import com.gitsurfer.gitsurf.model.AppRepository
import com.gitsurfer.gitsurf.ui.base.BaseViewModel
import javax.inject.Inject

class ReadmeViewModel @Inject constructor(
  val appRepository: AppRepository
) : BaseViewModel()