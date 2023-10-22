package me.choicore.likeapuppy.identityprovider.authentication

import me.choicore.likeapuppy.core.domain.user.model.Authentication
import me.choicore.likeapuppy.core.domain.user.model.Username
import me.choicore.likeapuppy.core.domain.user.model.aggregate.Account
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountCommandProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountQueryProcessor
import me.choicore.likeapuppy.identityprovider.common.Slf4j
import org.slf4j.Logger
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

/**
 * This class is responsible for listening to the authentication events
 * and updating the user account status accordingly.
 */
@Component
class AuthenticationEventListener(
    private val accountQuery: AccountQueryProcessor,
    private val accountCommand: AccountCommandProcessor,
) {
    private val logger: Logger = Slf4j

    private fun getAccount(
        username: String,
    ): Account {
        return accountQuery.findUserAccountByUsername(
            Username.withEmailType(username),
        )
    }

    /**
     * it means the user has been authenticated successfully
     *
     * @param success the event
     */
    @EventListener
    fun onSuccess(success: AuthenticationSuccessEvent) {
        logger.info("AuthenticationSuccessEvent: ${success.authentication.name}")
        if (success.authentication.principal is User) {
            val username: String = success.authentication.name
            val account: Account = getAccount(username)
            val accountAuthentication: Authentication = account.authentication.markAsLoggedIn()
            accountCommand.modifyAccountAuthentication(account.id, accountAuthentication)
        }
    }

    /**
     * it means the user has failed to authenticate due to bad credentials
     *
     * @param badCredentialsEvent the event
     */
    @EventListener
    fun onFailure(badCredentialsEvent: AuthenticationFailureBadCredentialsEvent) {
        val username: String = badCredentialsEvent.authentication.name
        val account: Account = getAccount(username)
        val accountAuthentication: Authentication = account.authentication.markAsFailedLogin()
        accountCommand.modifyAccountAuthentication(account.id, accountAuthentication)
        logger.info(
            "BadCredentialsException $username, failedLoginAttempts ${accountAuthentication.failedLoginAttempts} ",
        )
    }
}

//    /**
//     * it means the user has failed to authenticate
//     *
//     * @param failures the event
//     */
//    @EventListener
//    fun onFailure(failures: AbstractAuthenticationFailureEvent) {
//        logger.info("AbstractAuthenticationFailureEvent: ${failures.exception.message}")
//        when (failures.exception) {
//            is BadCredentialsException -> {
//                onFailure(failures as AuthenticationFailureBadCredentialsEvent)
//            }
//
//            is UsernameNotFoundException -> {}
//            is AccountExpiredException -> {}
//            is ProviderNotFoundException -> {}
//            is DisabledException -> {}
//            is LockedException -> {}
//            is AuthenticationServiceException -> {
//                logger.error("AuthenticationServiceException: ${failures.exception.message}", failures.exception)
//            }
//
//            is CredentialsExpiredException -> {}
//            else -> {
//                throw UnauthorizedException.UnknownError
//            }
//        }
//    }
