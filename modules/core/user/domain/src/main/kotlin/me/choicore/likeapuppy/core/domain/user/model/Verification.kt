package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Verification(
    val isEmailVerified: Boolean = false,
    val emailVerifiedAt: Instant? = null,
    val isPhoneNumberVerified: Boolean = false,
    val phoneNumberVerifiedAt: Instant? = null,
)
