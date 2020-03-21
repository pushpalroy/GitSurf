package com.gitsurfer.gitsurf.utils.exceptions

import com.gitsurfer.gitsurf.model.network.models.ResponseUnauthorized

class HttpNotSuccessException(val responseError: ResponseUnauthorized) :
    Exception(responseError.message)