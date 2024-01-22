package com.xaquare.xquarebackoffice.infrastructure.feign.client

import com.xaquare.xquarebackoffice.infrastructure.feign.client.model.User
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@FeignClient(name = "UserClient", url = "\${service.scheme}://\${service.user.host}")
interface UserClient {

    @GetMapping("/users/id/{userId}")
    fun getUser(@PathVariable userId: UUID): User
}