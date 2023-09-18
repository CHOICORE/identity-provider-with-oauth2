package me.choicore.likeapuppy.identityprovider.authentication

import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository
import me.choicore.likeapuppy.identityprovider.common.Slf4j
import me.choicore.likeapuppy.identityprovider.common.findByIdentifierOrNull
import me.choicore.likeapuppy.identityprovider.exception.UnauthorizedException
import org.slf4j.Logger
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.ProviderNotFoundException
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import me.choicore.likeapuppy.core.user.jpa.entity.User as UserEntity

/**
 * This class is responsible for listening to the authentication events
 * and updating the user account status accordingly.
 */
@Component
@Transactional(readOnly = true)
class AuthenticationEventListener(
    private val userRepository: UserRepository,
) {
    private val logger: Logger = Slf4j

    /**
     * it means the user has been authenticated successfully
     *
     * @param success the event
     */
    @EventListener
    @Transactional
    fun onSuccess(success: AuthenticationSuccessEvent) {
        logger.info("AuthenticationSuccessEvent: ${success.authentication.name}")

        if (success.authentication.principal is User) {
            val identifier: String = success.authentication.name
            val user: UserEntity = getUserInfo(identifier)
            /* mark the user account status as logged in */
            user.accountStatus.markAsLoggedIn()
        }
    }

    /**
     * it means the user has failed to authenticate
     *
     * @param failures the event
     */
    @EventListener
    @Transactional
    fun onFailure(failures: AbstractAuthenticationFailureEvent) {
        val userInfo: UserEntity = getUserInfo(failures.authentication.name)
        when (failures.exception) {
            is BadCredentialsException -> {
                /* mark the user account status as failed to log in */
                userInfo.accountStatus.markAsFailedToLogin()
                logger.info(
                    "BadCredentialsException ${userInfo.credential.identifier.email}, " +
                        "failedLoginAttempts ${userInfo.accountStatus.failedLoginAttempts} " +
                        "at ${Instant.now()}",
                )
            }

            is UsernameNotFoundException -> {}
            is AccountExpiredException -> {}
            is ProviderNotFoundException -> {}
            is DisabledException -> {}
            is LockedException -> {}
            is AuthenticationServiceException -> {
                logger.error("AuthenticationServiceException: ${failures.exception.message}", failures.exception)
            }

            is CredentialsExpiredException -> {}
            else -> {
                throw UnauthorizedException.UnknownError
            }
        }
    }

    /**
     * Loads the user from the database by the given identifier.
     *
     * @param identifier the email or mobile of the user to load
     * @return user
     * @throws UnauthorizedException.AccountNotFound if the user is not found
     */
    @Throws(UnauthorizedException::class)
    private fun getUserInfo(identifier: String): UserEntity {
        return userRepository.findByIdentifierOrNull(identifier) ?: throw UnauthorizedException.AccountNotFound
    }
}
