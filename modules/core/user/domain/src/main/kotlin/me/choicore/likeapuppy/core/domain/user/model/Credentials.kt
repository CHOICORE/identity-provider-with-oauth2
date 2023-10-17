package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator

data class Credentials(
    private val username: String,
    private val password: String,
) : Validator {

    override fun validate() {
        if (username.isBlank()) {
            throw IllegalArgumentException("Username must not be blank.")
        }
        if (password.isBlank()) {
            throw IllegalArgumentException("Password must not be blank.")
        }
    }
}
