package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

data class Agreement(
    val id: Long,
    val subject: String,
    val content: String,
    val description: String,
    val required: Boolean,
    val registeredAt: Instant,
    val modifiedAt: Instant,
)
