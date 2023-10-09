package me.choicore.likeapuppy.core.domain.user.command

import me.choicore.likeapuppy.core.domain.Validator
import me.choicore.likeapuppy.core.domain.user.model.DateOfBirth
import me.choicore.likeapuppy.core.domain.user.model.Username
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender

sealed interface RegisterUserCommand : Validator {
    val email: String
    val password: String
    val username: Username
    val dateOfBirth: DateOfBirth
    val gender: Gender
    val phoneNumber: String?
    val termsAndConditionsIds: List<Long>

    data class WithAuthorityNames(
        override val email: String,
        override val password: String,
        override val username: Username,
        override val dateOfBirth: DateOfBirth,
        override val gender: Gender,
        override val phoneNumber: String? = null,
        override val termsAndConditionsIds: List<Long>,
        val authorities: List<AuthorityNames>,
    ) : RegisterUserCommand, Validator {

        override fun validate() {
            check(this.authorities.isNotEmpty()) { "required" }
        }
    }

    data class WithAuthorityIds(
        override val email: String,
        override val password: String,
        override val username: Username,
        override val dateOfBirth: DateOfBirth,
        override val gender: Gender,
        override val phoneNumber: String? = null,
        override val termsAndConditionsIds: List<Long>,
        val authorityIds: List<Long>,
    ) : RegisterUserCommand, Validator {

        override fun validate() {
            check(this.authorityIds.isNotEmpty()) { "required" }
        }
    }

    override fun validate() {
        require(this.email.isNotBlank()) { "Email is required" }
        require(this.password.isNotBlank()) { "Password is required" }
        this.username.validate()
        this.dateOfBirth.validate()
        when (this) {
            is WithAuthorityNames -> this.validate()
            is WithAuthorityIds -> this.validate()
        }
    }
}
