package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator

data class User(
    val id: Long,
    val profile: Profile,
    val authentication: Authentication,
    val grantedAuthorities: List<GrantedAuthority>,
    val consents: List<Consent>,
    val verification: Verification,
) : Validator {

    override fun validate() {
    }
}
