package com.xaquare.xquarebackoffice.infrastructure.feign.config

import com.xaquare.xquarebackoffice.infrastructure.feign.FeignClientErrorDecoder
import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["com.xaquare.xquarebackoffice"])
@Configuration
class FeignConfig {
    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder(): FeignClientErrorDecoder = FeignClientErrorDecoder()
}
