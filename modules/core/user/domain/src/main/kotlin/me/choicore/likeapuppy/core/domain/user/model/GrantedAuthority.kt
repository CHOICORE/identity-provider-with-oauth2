package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import java.time.Instant

data class GrantedAuthority(
    val authority: AuthorityNames,
    val scope: String,
    val grantedAt: Instant,
)
