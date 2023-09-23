package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Authentication(
    val identifier: Identifier,
    val credentials: Credentials,
    val lastLoggedInAt: Instant? = null,
    val registeredAt: Instant,
    val isAccountNonExpired: Boolean = true,
    val isCredentialsNonExpired: Boolean = true,
    val isAccountNonLocked: Boolean = true,
)
