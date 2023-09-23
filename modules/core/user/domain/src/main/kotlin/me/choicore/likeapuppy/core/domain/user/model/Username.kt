package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator

/**
 * Username Model.
 *
 * @property firstName The first name of the user.
 * @property lastName The last name of the user.
 */
data class Username(
    val firstName: String,
    val lastName: String,
) : Validator {

    @Throws(IllegalArgumentException::class)
    override fun validate() {
        require(this.firstName.isNotBlank()) { "First name must not be blank." }
        require(this.lastName.isNotBlank()) { "Last name must not be blank." }
    }
}
