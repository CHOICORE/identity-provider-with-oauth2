package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Consent(
    val agreement: Agreement,
    val consented: Boolean,
    val consentedAt: Instant,
)
