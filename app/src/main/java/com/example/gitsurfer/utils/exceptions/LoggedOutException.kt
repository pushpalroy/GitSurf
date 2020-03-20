package com.example.gitsurfer.utils.exceptions

class LoggedOutException(val isUserAction: Boolean = false) : Exception("User logged out!")