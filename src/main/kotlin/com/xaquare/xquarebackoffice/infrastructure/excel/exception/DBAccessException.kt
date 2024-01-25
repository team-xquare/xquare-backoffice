package com.xaquare.xquarebackoffice.infrastructure.excel.exception

import com.xaquare.xquarebackoffice.global.error.exception.ErrorCode
import com.xaquare.xquarebackoffice.global.error.exception.XquareException

object DBAccessException : XquareException(
    ErrorCode.DB_ACCESS_ERROR
)