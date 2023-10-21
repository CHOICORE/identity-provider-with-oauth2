package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.common.extension.plusDays
import java.time.Instant

object AuthenticationProperties {
    const val DEFAULT_LOGIN_ATTEMPTS_LIMIT: Int = 5
    const val DEFAULT_PASSWORD_EXPIRATION_DAYS: Long = 90
    const val DEFAULT_DORMANT_DAYS: Long = 365
}

data class Authentication(
    val failedLoginAttempts: Int = 0,
    val passwordExpirationAt: Instant,
    val lastLoggedInAt: Instant,
) {
    private val instancedAt: Instant = Instant.now()
    val isCredentialsNonExpired: Boolean
    val isAccountNonExpired: Boolean
    val isAccountNonLocked: Boolean

    init {
        if (passwordExpirationAt.isBefore(instancedAt)) {
            throw IllegalArgumentException("passwordExpirationAt must be after instancedAt")
        }
        isCredentialsNonExpired = setCredentialNonExpired()
        isAccountNonExpired = setAccountNonExpired()
        isAccountNonLocked = setAccountNonLocked()
    }

    private fun setAccountNonLocked(): Boolean {
        return failedLoginAttempts <= AuthenticationProperties.DEFAULT_LOGIN_ATTEMPTS_LIMIT
    }

    private fun setAccountNonExpired(): Boolean {
        return lastLoggedInAt.plusDays(AuthenticationProperties.DEFAULT_DORMANT_DAYS).isAfter(instancedAt)
    }

    private fun setCredentialNonExpired(): Boolean {
        return passwordExpirationAt.isAfter(instancedAt)
    }

    fun markAsLoggedIn(): Authentication {
        return this.copy(
            failedLoginAttempts = 0,
            lastLoggedInAt = Instant.now(),
        )
    }

    fun markAsFailedLogin(): Authentication {
        return this.copy(
            failedLoginAttempts = this.failedLoginAttempts + 1,
        )
    }

    fun updatePasswordExpirationAt(): Authentication {
        return this.copy(
            passwordExpirationAt =
            Instant.now()
                .plusDays(AuthenticationProperties.DEFAULT_PASSWORD_EXPIRATION_DAYS),
        )
    }
}
