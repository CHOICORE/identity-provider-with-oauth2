package me.choicore.likeapuppy.identityprovider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class IdentityProviderApplication

fun main(args: Array<String>) {
	runApplication<IdentityProviderApplication>(*args)
}