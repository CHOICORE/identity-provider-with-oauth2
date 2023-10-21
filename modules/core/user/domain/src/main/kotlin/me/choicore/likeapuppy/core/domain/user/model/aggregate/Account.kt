package me.choicore.likeapuppy.core.domain.user.model.aggregate

import me.choicore.likeapuppy.core.domain.user.model.Authentication
import me.choicore.likeapuppy.core.domain.user.model.Credentials
import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority
import java.time.Instant

data class Account(
    val id: Long,
    val credentials: Credentials,
    val authentication: Authentication,
    val authorities: List<GrantedAuthority>,
    val registeredAt: Instant,
) {
    val username: String = credentials.username
    val password: String = credentials.password
    val isAccountNonExpired: Boolean = authentication.isAccountNonExpired
    val isCredentialsNonExpired: Boolean = authentication.isCredentialsNonExpired
    val isAccountNonLocked: Boolean = authentication.isAccountNonLocked
    val isEnabled: Boolean =
        isAccountNonLocked && isAccountNonExpired && isCredentialsNonExpired
}
