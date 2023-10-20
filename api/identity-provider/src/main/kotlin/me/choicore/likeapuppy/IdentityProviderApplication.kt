package me.choicore.likeapuppy

import me.choicore.likeapuppy.identityprovider.configuration.annotation.EnableJpaConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@EnableJpaConfiguration
@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = ["me.choicore.likeapuppy"])
class IdentityProviderApplication

fun main(args: Array<String>) {
    runApplication<IdentityProviderApplication>(*args)
}
