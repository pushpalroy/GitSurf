package com.example.gitsurfer.model.usecases.exceptions

sealed class ValidationException : Exception() {
  object UsernameEmpty : ValidationException()
  object PasswordEmpty : ValidationException()
  object OperationInQueue : ValidationException()
  object NoJobSelected : ValidationException()
  object NoOperationalUnitSelected : ValidationException()
  object NoOperatedTime : ValidationException()
  object NoIdleTime : ValidationException()
  object BothTimesAreZero : ValidationException()
  object TotalsHoursExceedHoursInDay : ValidationException()
  object MissingLoginLogoutTimes : ValidationException()
}
