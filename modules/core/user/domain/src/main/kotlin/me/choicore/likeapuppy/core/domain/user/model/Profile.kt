package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator
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

    data class PersonalName(
        val firstName: String,
        val middleName: String? = null,
        val lastName: String,
    ) : Validator {

        enum class DisplayNameOrders {
            FIRST_LAST,
            LAST_FIRST,
        }

        fun fullName(order: DisplayNameOrders = DisplayNameOrders.LAST_FIRST): String {
            return when (order) {
                DisplayNameOrders.FIRST_LAST -> "$firstName ${middleName ?: ""} $lastName"
                DisplayNameOrders.LAST_FIRST -> "$lastName$firstName"
            }
        }

        override fun validate() {
            require(this.firstName.isNotBlank()) { "First name must not be blank." }
            require(this.lastName.isNotBlank()) { "Last name must not be blank." }
        }
    }
}
