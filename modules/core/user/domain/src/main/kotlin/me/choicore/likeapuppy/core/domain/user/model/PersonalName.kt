package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.common.Validator

data class PersonalName(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
) : Validator {
    constructor(firstName: String, lastName: String) : this(firstName, null, lastName)

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
