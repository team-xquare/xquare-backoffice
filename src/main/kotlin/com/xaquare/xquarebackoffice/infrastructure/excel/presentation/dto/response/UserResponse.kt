package com.xaquare.xquarebackoffice.infrastructure.excel.presentation.dto.response

data class UserResponse(
    val name: String,
    val accountId: String,
    val password: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profile: String
)
