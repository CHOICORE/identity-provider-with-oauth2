package me.choicore.likeapuppy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class RelyingPartyApplication

fun main(args: Array<String>) {
    runApplication<RelyingPartyApplication>(*args)
}
