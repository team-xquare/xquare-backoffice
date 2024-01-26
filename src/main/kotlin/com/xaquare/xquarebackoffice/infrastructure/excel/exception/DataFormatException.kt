package com.xaquare.xquarebackoffice.infrastructure.excel.exception

import com.xaquare.xquarebackoffice.global.error.exception.ErrorCode
import com.xaquare.xquarebackoffice.global.error.exception.XquareException

object DataFormatException : XquareException(
    ErrorCode.DATA_FORMAT_BAD_REQUEST
)
