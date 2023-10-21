package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.common.Validator
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender
import java.time.LocalDate

data class Profile(
    val id: Long,
    val nickname: String?,
    val email: String,
    val phoneNumber: String?,
    val personalName: PersonalName,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val aboutMe: String? = null,
) : Validator {
    override fun validate() {
        this.personalName.validate()
    }
}
