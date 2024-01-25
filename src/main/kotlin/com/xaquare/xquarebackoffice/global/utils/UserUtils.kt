package com.xaquare.xquarebackoffice.global.utils

import com.xaquare.xquarebackoffice.infrastructure.feign.client.UserClient
import com.xaquare.xquarebackoffice.infrastructure.feign.client.model.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserUtils(
    private val userClient: UserClient
) {
    fun getUser(): UUID = UUID.fromString(SecurityContextHolder.getContext().authentication.name)
    fun getCurrentUserId(): User = userClient.getUser(getUser())
}
