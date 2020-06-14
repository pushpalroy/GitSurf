package com.gitsurfer.gitsurf.utils.exceptions

import com.gitsurfer.gitsurf.data.network.models.ResponseUnauthorized

class HttpNotSuccessException(val responseError: ResponseUnauthorized) :
    Exception(responseError.message)