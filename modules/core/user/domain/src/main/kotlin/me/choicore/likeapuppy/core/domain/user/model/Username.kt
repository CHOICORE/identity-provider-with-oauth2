package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.common.Validator
import me.choicore.likeapuppy.core.domain.user.model.constant.UsernameTypeHints

data class Username(
    val username: String,
    val typeHints: UsernameTypeHints = UsernameTypeHints.EMAIL,
) : Validator {

    override fun validate() {
        require(this.username.isNotBlank()) { "Username is required" }
    }

    companion object {
        fun withEmailType(username: String): Username {
            return Username(username, UsernameTypeHints.EMAIL)
        }

        fun withPhoneNumberType(username: String): Username {
            return Username(username, UsernameTypeHints.PHONE_NUMBER)
        }
    }
}
