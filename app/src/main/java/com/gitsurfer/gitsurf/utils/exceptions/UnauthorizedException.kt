package com.gitsurfer.gitsurf.utils.exceptions

class UnauthorizedException : Exception(
    "Bad credentials. Authenticating with invalid credentials will return 401 Unauthorized."
)