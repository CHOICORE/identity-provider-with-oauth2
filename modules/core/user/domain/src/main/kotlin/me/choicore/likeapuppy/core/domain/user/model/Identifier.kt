package me.choicore.likeapuppy.core.domain.user.model

data class Identifier(
    val email: String,
    val phoneNumber: String? = null,
)
