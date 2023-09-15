package me.choicore.likeapuppy.identityprovider.authentication

import me.choicore.likeapuppy.core.user.jpa.entity.User
import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository
import me.choicore.likeapuppy.identityprovider.common.findByIdentifierOrNull
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.stereotype.Service

@Service
class OidcUserInfoService(
    private val userRepository: UserRepository,
) {

    fun loadUser(username: String): OidcUserInfo {
        return OidcUserInfo(loadUserAttributes(username))
    }

    private fun loadUserAttributes(username: String): Map<String, Any> {
        val user: User = this.userRepository.findByIdentifierOrNull(username)
            ?: throw UsernameNotFoundException("User with identifier [$username] could not be found.")

        return oidcUserInfo {
            subject(username)
            givenName(user.username.firstName)
            familyName(user.username.lastName)
            nickname(user.nickname)
            preferredUsername(username)
            picture(user.picture)
            email(user.credential.identifier.email)
            emailVerified(user.accountStatus.isEmailVerified)
            gender(user.gender.name)
            phoneNumber(user.credential.identifier.mobile)
            phoneNumberVerified(user.accountStatus.isMobileVerified)
        }
    }
}

internal fun oidcUserInfo(block: OidcUserInfo.Builder.() -> Unit): Map<String, Any> {
    return OidcUserInfo.builder().apply(block).build().claims
}
