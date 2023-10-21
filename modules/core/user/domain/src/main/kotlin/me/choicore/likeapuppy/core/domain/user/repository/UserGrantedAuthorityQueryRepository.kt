package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority

interface UserGrantedAuthorityQueryRepository {
    fun findUserGrantedAuthoritiesByUsername(username: String): List<GrantedAuthority>
}
