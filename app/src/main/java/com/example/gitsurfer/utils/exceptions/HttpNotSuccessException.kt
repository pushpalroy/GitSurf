package com.example.gitsurfer.utils.exceptions

import com.example.gitsurfer.model.network.models.ResponseUnauthorized

class HttpNotSuccessException(val responseError: ResponseUnauthorized) :
    Exception(responseError.message)