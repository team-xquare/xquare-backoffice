package com.xaquare.xquarebackoffice.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    FEIGN_BAD_REQUEST(400, "Feign Bad Request"),
    FEIGN_UNAUTHORIZED(401, "Feign UnAuthorized"),
    FEIGN_FORBIDDEN(403, "Feign Forbidden"),
    FEIGN_SERVER_ERROR(500, "Feign Server Error"),
    DATA_FORMAT_BAD_REQUEST(400, "data Format Bad Request"),

    USER_NOT_FOUND(404, "User Not Found"),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    DB_ACCESS_ERROR(500, "DB Access Error")
}
