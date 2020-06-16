package com.gitsurfer.gitsurf.utils.exceptions

sealed class ValidationException : Exception() {
  object UsernameEmpty : ValidationException()
  object PasswordEmpty : ValidationException()
  object OperationInQueue : ValidationException()
}
