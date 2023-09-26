package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator

data class Identifier(
    val email: String,
    val phoneNumber: String? = null,
) : Validator {

    override fun validate() {
    }
}
