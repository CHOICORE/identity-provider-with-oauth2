package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator
import java.time.Instant

data class Authentication(
    val failedLoginAttempts: Int = 0,
    val passwordExpirationAt: Instant,
    val lastLoggedInAt: Instant,
    val isAccountNonExpired: Boolean = false,
    val isCredentialsNonExpired: Boolean = false,
    val isAccountNonLocked: Boolean = false,
) : Validator {

    override fun validate() {
    }
}
