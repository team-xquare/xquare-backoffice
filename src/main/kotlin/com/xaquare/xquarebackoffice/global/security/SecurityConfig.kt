package com.xaquare.xquarebackoffice.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val SCHOOL = "SCH"
    }

    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .cors().and()
            .formLogin().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest)
            .permitAll()
            .antMatchers("/excel/**")
            .hasRole(SCHOOL)

        return http.build()
    }
}
