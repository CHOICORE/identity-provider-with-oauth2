package me.choicore.likeapuppy.identityprovider.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "authentication")
data class AuthenticationProperties(
    val loginAttemptsLimit: Int,
)
