package me.choicore.likeapuppy.core.domain.user.model

import java.time.Instant

/**
 * Authentication Model.
 *
 * @property identifier The identifier of the user. (e.g. email, phone number.)
 * @property credentials The credentials of the user.
 * @property lastLoggedInAt The last logged in date time of the user.
 * @property registeredAt The registered date time of the user.
 * @property isAccountNonExpired Whether the account is expired or not.
 * @property isCredentialsNonExpired Whether the credentials are expired or not.
 * @property isAccountNonLocked Whether the account is locked or not.
 */
// TODO: 외부 IDP 에 인증이 위임 된 경우, IDP 식별 정보를 저장할 필요가 있음.
data class Authentication(
    val identifier: Identifier,
    val credentials: Credentials,
    val lastLoggedInAt: Instant? = null,
    val registeredAt: Instant,
)
