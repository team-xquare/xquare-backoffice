package com.xaquare.xquarebackoffice.infrastructure.feign.exception

import com.xaquare.xquarebackoffice.global.error.exception.ErrorCode
import com.xaquare.xquarebackoffice.global.error.exception.XquareException

object FeignServerError : XquareException(
    ErrorCode.FEIGN_SERVER_ERROR
)
