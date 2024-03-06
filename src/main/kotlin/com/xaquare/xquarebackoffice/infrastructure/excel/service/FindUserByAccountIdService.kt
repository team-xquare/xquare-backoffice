package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.domain.persistence.UserPersistenceAdapter
import com.xaquare.xquarebackoffice.infrastructure.excel.exception.UserNotFoundException
import com.xaquare.xquarebackoffice.infrastructure.excel.presentation.dto.response.UserResponse
import org.springframework.stereotype.Service

@Service
class FindUserByAccountIdService(
    private val userPersistenceAdapter: UserPersistenceAdapter
) {

    fun execute(accountId: String): UserResponse {
        val user = userPersistenceAdapter.findUserByAccountId(accountId) ?: throw UserNotFoundException

        return UserResponse(
            user.name,
            user.accountId,
            user.password,
            user.grade,
            user.classNum,
            user.num,
            user.profile!!
        )
    }
}
