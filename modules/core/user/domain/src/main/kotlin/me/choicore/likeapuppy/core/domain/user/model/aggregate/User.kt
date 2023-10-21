package me.choicore.likeapuppy.core.domain.user.model.aggregate

import me.choicore.likeapuppy.core.common.Validator
import me.choicore.likeapuppy.core.domain.user.model.Authentication
import me.choicore.likeapuppy.core.domain.user.model.ConsentedTermsAndConditions
import me.choicore.likeapuppy.core.domain.user.model.Credentials
import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority
import me.choicore.likeapuppy.core.domain.user.model.Profile
import me.choicore.likeapuppy.core.domain.user.model.Verification
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
