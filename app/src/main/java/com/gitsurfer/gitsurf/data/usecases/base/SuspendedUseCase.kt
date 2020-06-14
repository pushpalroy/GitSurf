package com.gitsurfer.gitsurf.data.usecases.base

/**
 * Base use case that takes in executable parameters and returns results
 */

abstract class SuspendedUseCase<out Results, in ExecutableParam> {
  /**
   * Executes the current use case and returns the result.
   * If this should not return anything then use [Unit] as [Results].
   */
  abstract suspend fun perform(executableParam: ExecutableParam?): Results
}