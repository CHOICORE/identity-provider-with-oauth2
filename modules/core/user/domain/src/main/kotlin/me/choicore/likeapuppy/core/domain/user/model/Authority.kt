package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import java.time.Instant

data class Authority(
    val id: Long,
    val name: AuthorityNames,
    val scope: String,
    val description: String,
    val registeredAt: Instant,
)
