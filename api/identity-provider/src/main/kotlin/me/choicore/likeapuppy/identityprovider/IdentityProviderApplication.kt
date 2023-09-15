package me.choicore.likeapuppy.identityprovider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["me.choicore.likeapuppy.core"])
@EnableJpaRepositories(basePackages = ["me.choicore.likeapuppy.core"])
@SpringBootApplication(scanBasePackages = ["me.choicore.likeapuppy"])
@ConfigurationPropertiesScan
class IdentityProviderApplication

fun main(args: Array<String>) {
    runApplication<IdentityProviderApplication>(*args)
}
