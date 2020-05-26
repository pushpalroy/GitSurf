package com.gitsurfer.gitsurf.utils.exceptions

class ForbiddenException(val isUserAction: Boolean = false) : Exception(
    "Maximum number of login attempts exceeded. Please try again later. " +
        "After detecting several requests with invalid credentials within a short period, " +
        "the API will temporarily reject all authentication attempts for that user " +
        "(including ones with valid credentials) with 403 Forbidden."
)