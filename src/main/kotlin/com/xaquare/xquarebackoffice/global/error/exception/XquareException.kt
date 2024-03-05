package com.xaquare.xquarebackoffice.global.error.exception

abstract class XquareException(
    val errorCode: ErrorCode
) : RuntimeException()
