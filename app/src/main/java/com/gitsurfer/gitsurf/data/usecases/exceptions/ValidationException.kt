package com.gitsurfer.gitsurf.data.usecases.exceptions

sealed class ValidationException : Exception() {
  object UsernameEmpty : ValidationException()
  object PasswordEmpty : ValidationException()
  object OperationInQueue : ValidationException()
}
