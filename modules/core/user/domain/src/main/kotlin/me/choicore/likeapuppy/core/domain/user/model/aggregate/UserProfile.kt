package me.choicore.likeapuppy.core.domain.user.model.aggregate

import me.choicore.likeapuppy.core.common.Validator
import me.choicore.likeapuppy.core.domain.user.model.Profile
import me.choicore.likeapuppy.core.domain.user.model.Verification

data class UserProfile(
    val id: Long,
    val profile: Profile,
    val verification: Verification,
) : Validator {
    override fun validate() {}
}
