package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class TermsAndConditions(
    val id: Long,
    val subject: String,
    val content: String,
    val description: String,
    val mandatory: Boolean,
    val used: Boolean,
    val version: String,
    val registeredAt: Instant,
    val modifiedAt: Instant,
)
