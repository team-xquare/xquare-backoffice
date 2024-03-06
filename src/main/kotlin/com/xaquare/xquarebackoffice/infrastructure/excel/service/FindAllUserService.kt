package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.domain.persistence.UserPersistenceAdapter
import com.xaquare.xquarebackoffice.infrastructure.excel.presentation.dto.response.UserResponse
import org.springframework.stereotype.Service

@Service
class FindAllUserService(
    private val userPersistenceAdapter: UserPersistenceAdapter
) {

    fun execute(): List<UserResponse> {
        val userData = userPersistenceAdapter.findAll()

        return userData.map { user ->
            UserResponse(
                name = user.name,
                accountId = user.accountId,
                password = user.password,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                profile = user.profile!!
            )
        }
    }
}
