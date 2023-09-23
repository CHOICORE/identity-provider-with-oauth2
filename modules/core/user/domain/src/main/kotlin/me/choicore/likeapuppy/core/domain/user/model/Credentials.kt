package me.choicore.likeapuppy.core.domain.user.model

import me.choicore.likeapuppy.core.domain.Validator
import java.time.Instant

/**
 * User's credentials.
 *
 * @property password User's password.
 * @property failedLoginAttempts Number of failed login attempts.
 * @property passwordExpirationAt Password expiration time.
 * @constructor Creates a new instance of [Credentials].
 * @see User
 */
data class Credentials(
    val password: String,
    val failedLoginAttempts: Int = 0,
    val passwordExpirationAt: Instant,
) : Validator {

    override fun validate() {
    }
}
