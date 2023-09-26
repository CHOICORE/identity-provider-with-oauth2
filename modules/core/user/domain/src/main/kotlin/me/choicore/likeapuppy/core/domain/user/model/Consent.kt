package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Consent(
    val agreements: List<Agreement>,
    val accepted: Boolean,
    val consentedAt: Instant,
)
