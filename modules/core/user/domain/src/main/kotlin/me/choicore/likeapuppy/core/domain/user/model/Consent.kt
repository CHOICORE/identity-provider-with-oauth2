package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Consent(
    val termsAndConditions: TermsAndConditions,
    val consented: Boolean,
    val consentedAt: Instant,
)
