package me.choicore.likeapuppy.identityprovider.authentication

import me.choicore.likeapuppy.core.user.jpa.entity.AccountStatus
import me.choicore.likeapuppy.core.user.jpa.entity.GrantedAuthority
import me.choicore.likeapuppy.core.user.jpa.entity.User
import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository
import me.choicore.likeapuppy.identityprovider.common.findByIdentifierOrNull
import me.choicore.likeapuppy.identityprovider.common.properties.AuthenticationProperties
import me.choicore.likeapuppy.identityprovider.exception.UnauthorizedException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Component
@Transactional(readOnly = true)
class UserDetailsService(
	private val authenticationProperties: AuthenticationProperties,
	private val userRepository: UserRepository,
) : UserDetailsService {

	/**
	 * Loads a user from the database using the provided identifier.
	 *
	 * @param identifier The email or mobile number used to identify the user to be loaded.
	 * @return A [UserDetails] object that corresponds to the given identifier.
	 * @throws UnauthorizedException.UsernameNotFound if the user could not be found.
	 */
	@Throws(UnauthorizedException.UsernameNotFound::class)
	override fun loadUserByUsername(
		identifier: String,
	): UserDetails {
		val userInfo: User = getUserInfo(identifier)
		return toAuthenticationObject(userInfo)
	}

	private fun toAuthenticationObject(
		userInfo: User,
	) =
		org.springframework.security.core.userdetails.User(
			userInfo.credential.identifier.email,
			userInfo.credential.password,
			userInfo.accountStatus.isEnable,
			userInfo.accountStatus.isAccountNonExpired,
			userInfo.accountStatus.isCredentialsNonExpired,
			userInfo.accountStatus.isAccountNonLocked,
			getGrantedAuthorities(userInfo),
		)

	/**
	 * Retrieves a user from the database using the provided identifier.
	 * @param identifier The email or mobile number used to identify the user to be loaded.
	 * @return A [User] object that corresponds to the given identifier.
	 * @throws UnauthorizedException.UsernameNotFound if the user could not be found.
	 */
	@Throws(UnauthorizedException.UsernameNotFound::class)
	private fun getUserInfo(identifier: String): User = (
			userRepository.findByIdentifierOrNull(identifier)
				?: throw UnauthorizedException.UsernameNotFound
			)

	/**
	 * Retrieves a collection of authorities that have been granted to the specified user.
	 *
	 * @param user The [User] for whom the authorities are to be retrieved.
	 * @return A collection of [SimpleGrantedAuthority] objects representing the authorities granted to the user.
	 */
	private fun getGrantedAuthorities(user: User): MutableSet<SimpleGrantedAuthority> = user.grantedAuthorities
		.mapTo(mutableSetOf()) { grantedAuthority: GrantedAuthority ->
			SimpleGrantedAuthority(grantedAuthority.authority.name)
		}

	private val AccountStatus.isEnable
		get() = isAccountNonExpired && isAccountNonLocked && isCredentialsNonExpired

	private val AccountStatus.isAccountNonExpired
		get() = !isDormant()

	private val AccountStatus.isAccountNonLocked
		get() = !isLoginAttemptsExceeded(authenticationProperties.loginAttemptsLimit) && !isDormant()

	private val AccountStatus.isCredentialsNonExpired
		get() = if (this.passwordExpirationAt == null) {
			true
		} else {
			this.passwordExpirationAt.isBefore(Instant.now())
		}
}