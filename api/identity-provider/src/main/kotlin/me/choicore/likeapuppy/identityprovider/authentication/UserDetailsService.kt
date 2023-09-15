package me.choicore.likeapuppy.identityprovider.authentication

import me.choicore.likeapuppy.core.user.jpa.entity.AccountStatus
import me.choicore.likeapuppy.core.user.jpa.entity.GrantedAuthority
import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository
import me.choicore.likeapuppy.identityprovider.common.findByIdentifierOrNull
import me.choicore.likeapuppy.identityprovider.common.properties.AuthenticationProperties
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import me.choicore.likeapuppy.core.user.jpa.entity.User as UserEntity

/**
 * A [UserDetailsService] implementation that retrieves user details from the database.
 *
 * @param authenticationProperties The [AuthenticationProperties] object that contains the authentication properties.
 * @param userRepository The [UserRepository] object that is used to retrieve user details from the database.
 */
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
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(identifier: String): UserDetails {
        val userInfo: UserEntity = getUserInfo(identifier)
        return toAuthenticationObject(userInfo)
    }

    /**
     * Converts a [UserEntity] object to an [UserDetails] object that Spring Security understands.
     *
     * @param userInfo The [UserEntity] object to be converted.
     * @return An [UserDetails] object that Spring Security can use for authentication and validation.
     */
    private fun toAuthenticationObject(userInfo: UserEntity): User = User(
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
     * @return A [UserEntity] object that corresponds to the given identifier.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Throws(UsernameNotFoundException::class)
    private fun getUserInfo(identifier: String): UserEntity = userRepository.findByIdentifierOrNull(identifier) ?: throw UsernameNotFoundException(
        "User with identifier [$identifier] could not be found.",
    )

    /**
     * Retrieves a collection of authorities that have been granted to the specified user.
     *
     * @param user The [UserEntity] for whom the authorities are to be retrieved.
     * @return A collection of [SimpleGrantedAuthority] objects representing the authorities granted to the user.
     */
    private fun getGrantedAuthorities(user: UserEntity): MutableSet<SimpleGrantedAuthority> = user.grantedAuthorities.mapTo(mutableSetOf()) { grantedAuthority: GrantedAuthority ->
        SimpleGrantedAuthority(grantedAuthority.authority.name)
    }

    private val AccountStatus.isEnable
        get() = isAccountNonExpired && isAccountNonLocked && isCredentialsNonExpired

    private val AccountStatus.isAccountNonExpired
        get() = !isDormant()

    private val AccountStatus.isAccountNonLocked
        get() = !isLoginAttemptsExceeded(authenticationProperties.loginAttemptsLimit) && !isDormant()

    private val AccountStatus.isCredentialsNonExpired
        get() = this.passwordExpirationAt?.isBefore(Instant.now()) ?: true
}
