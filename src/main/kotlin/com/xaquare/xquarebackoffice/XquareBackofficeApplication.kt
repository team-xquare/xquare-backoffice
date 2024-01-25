package com.xaquare.xquarebackoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class XquareBackofficeApplication

fun main(args: Array<String>) {
	runApplication<XquareBackofficeApplication>(*args)
}
