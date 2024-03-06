package com.xaquare.xquarebackoffice.infrastructure.excel.exception

import com.xaquare.xquarebackoffice.global.error.exception.ErrorCode
import com.xaquare.xquarebackoffice.global.error.exception.XquareException

object UserNotFoundException : XquareException (
    ErrorCode.USER_NOT_FOUND
)
