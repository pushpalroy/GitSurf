package com.gitsurfer.gitsurf.model.usecases.exceptions

sealed class ValidationException : Exception() {
  object UsernameEmpty : ValidationException()
  object PasswordEmpty : ValidationException()
  object OperationInQueue : ValidationException()
}
