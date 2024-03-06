package com.xaquare.xquarebackoffice.domain.persistence.repository

import com.xaquare.xquarebackoffice.domain.entity.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, UUID> {
    fun findUserByAccountId(accountId: String): User?
}
