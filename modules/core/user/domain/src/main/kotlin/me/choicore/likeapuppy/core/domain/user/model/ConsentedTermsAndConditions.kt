package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class ConsentedTermsAndConditions(
    val termsAndConditions: TermsAndConditions,
    val consented: Boolean,
    val consentedAt: Instant,
)
