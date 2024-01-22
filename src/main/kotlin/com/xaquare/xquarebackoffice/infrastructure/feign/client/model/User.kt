package com.xaquare.xquarebackoffice.infrastructure.feign.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.xaquare.xquarebackoffice.global.security.UserRole
import java.util.UUID

data class User (
    val id: UUID,
    val name: String,
    @JsonProperty("account_id")
    val accountId: String,
    val userRole: UserRole
)