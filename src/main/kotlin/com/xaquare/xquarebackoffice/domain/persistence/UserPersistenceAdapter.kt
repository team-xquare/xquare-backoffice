package com.xaquare.xquarebackoffice.domain.persistence

import com.xaquare.xquarebackoffice.domain.entity.User
import com.xaquare.xquarebackoffice.domain.persistence.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) {
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findUserByAccountId(accountId: String): User? {
        return userRepository.findUserByAccountId(accountId)
    }
}
