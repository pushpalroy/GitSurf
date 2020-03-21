package com.gitsurfer.gitsurf.model.usecases.base

/**
 * Base use case that takes Prepare parameters, executable parameters and returns result
 */
abstract class SuspendedPrepareUseCase<out Results, in PrepareParams, in ExecutableParam> :
    SuspendedUseCase<Results, ExecutableParam>() {

  abstract suspend fun prepare(params: PrepareParams)
}