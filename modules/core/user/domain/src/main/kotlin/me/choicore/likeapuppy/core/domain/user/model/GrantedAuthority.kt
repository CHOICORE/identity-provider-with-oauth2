package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.user.model.constant.Authority
import java.time.Instant

data class GrantedAuthority(
    val authority: Authority,
    val grantedAt: Instant,
)
