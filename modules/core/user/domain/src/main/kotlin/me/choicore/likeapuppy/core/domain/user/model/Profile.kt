package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender

data class Profile(
    val nickname: String,
    val username: Username,
    val dateOfBirth: DateOfBirth,
    val gender: Gender,
    val aboutMe: String? = null,
) : Validator {

    override fun validate() {
    }
}
