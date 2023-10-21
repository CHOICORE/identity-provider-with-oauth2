package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Verification(
    val isEmailVerified: Boolean = false,
    val emailVerifiedAt: Instant? = null,
    val isPhoneNumberVerified: Boolean = false,
    val phoneNumberVerifiedAt: Instant? = null,
) {
    fun markAsEmailVerified(): Verification {
        return this.copy(
            isEmailVerified = true,
            emailVerifiedAt = Instant.now(),
        )
    }

    fun markAsPhoneNumberVerified(): Verification {
        return this.copy(
            isPhoneNumberVerified = true,
            phoneNumberVerifiedAt = Instant.now(),
        )
    }

    fun isVerified(): Boolean {
        return isEmailVerified || isPhoneNumberVerified
    }
}
