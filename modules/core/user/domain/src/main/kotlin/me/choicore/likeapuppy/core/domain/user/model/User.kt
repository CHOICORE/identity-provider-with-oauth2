package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator
import java.time.Instant

data class User(
    val id: Long,
    val credentials: Credentials,
    val authentication: Authentication,
    val profile: Profile,
    val authorities: List<GrantedAuthority>,
    val consents: List<ConsentedTermsAndConditions>,
    val verification: Verification,
    val registeredAt: Instant,
) : Validator {

    override fun validate() {}
}
