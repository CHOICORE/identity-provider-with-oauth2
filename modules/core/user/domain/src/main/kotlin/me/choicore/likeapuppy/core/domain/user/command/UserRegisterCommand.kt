package me.choicore.likeapuppy.core.domain.user.command

import me.choicore.likeapuppy.core.common.Validator
import me.choicore.likeapuppy.core.domain.user.model.PersonalName
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender
import java.time.LocalDate

sealed interface UserRegisterCommand : Validator {
    val email: String
    val password: String
    val personalName: PersonalName
    val dateOfBirth: LocalDate
    val gender: Gender
    val phoneNumber: String?
    val termsAndConditionsIds: List<Long>

    data class ContainsAuthorityNames(
        override val email: String,
        override val password: String,
        override val personalName: PersonalName,
        override val dateOfBirth: LocalDate,
        override val gender: Gender,
        override val phoneNumber: String? = null,
        override val termsAndConditionsIds: List<Long>,
        val authorityNames: List<AuthorityNames>,
    ) : UserRegisterCommand, Validator {
        override fun validate() {
            check(this.authorityNames.isNotEmpty()) { "required" }
        }
    }

    data class ContainsAuthorityIds(
        override val email: String,
        override val password: String,
        override val personalName: PersonalName,
        override val dateOfBirth: LocalDate,
        override val gender: Gender,
        override val phoneNumber: String? = null,
        override val termsAndConditionsIds: List<Long>,
        val authorityIds: List<Long>,
    ) : UserRegisterCommand, Validator {
        override fun validate() {
            check(this.authorityIds.isNotEmpty()) { "required" }
        }
    }

    override fun validate() {
        require(this.email.isNotBlank()) { "Email is required" }
        require(this.password.isNotBlank()) { "Password is required" }
        this.personalName.validate()
        when (this) {
            is ContainsAuthorityNames -> this.validate()
            is ContainsAuthorityIds -> this.validate()
        }
    }
}
