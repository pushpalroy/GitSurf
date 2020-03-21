package com.gitsurfer.gitsurf.utils.exceptions

class LoggedOutException(val isUserAction: Boolean = false) : Exception("User logged out!")