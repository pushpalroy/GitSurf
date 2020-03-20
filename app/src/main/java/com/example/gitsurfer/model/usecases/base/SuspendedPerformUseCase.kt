package com.example.gitsurfer.model.usecases.base

/**
 * Base use case that does not take any input parameters and returns results
 */

abstract class SuspendedPerformUseCase<out Results> {
  abstract suspend fun perform(): Results
}