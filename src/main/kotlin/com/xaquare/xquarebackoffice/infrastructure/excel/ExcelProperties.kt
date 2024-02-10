package com.xaquare.xquarebackoffice.infrastructure.excel

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("excel")
class ExcelProperties (
     val scheme: String,
     val host: String,
     val port: Int,
     val database: String,
     val username: String,
     val password: String
)
