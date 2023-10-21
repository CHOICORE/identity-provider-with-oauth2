package me.choicore.likeapuppy.identityprovider.exception

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.ProviderNotFoundException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException

internal const val UNAUTHORIZED_ERROR = "UNAUTHORIZED_ERROR"

sealed class UnauthorizedException(
    override val message: String,
    val errorCode: UnauthorizedErrorCode,
) : AuthenticationException(
    message,
) {
    // Invalid Credentials
    data object AccountNotFound : UnauthorizedException(
        message = "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.",
        errorCode = UnauthorizedErrorCode.ACCOUNT_NOT_FOUND,
    ) {
        private fun readResolve(): Any = AccountNotFound
    }

    data object InvalidPassword : UnauthorizedException(
        message = "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.",
        errorCode = UnauthorizedErrorCode.INVALID_PASSWORD,
    ) {
        private fun readResolve(): Any = InvalidPassword
    }

    data object InvalidToken : UnauthorizedException(
        message = "유효하지 않은 토큰입니다.",
        errorCode = UnauthorizedErrorCode.INVALID_TOKEN,
    ) {
        private fun readResolve(): Any = InvalidToken
    }

    data object ExpiredToken : UnauthorizedException(
        message = "만료된 토큰입니다.",
        errorCode = UnauthorizedErrorCode.EXPIRED_TOKEN,
    ) {
        private fun readResolve(): Any = ExpiredToken
    }

    // Inactive Account
    data object DormantAccount : UnauthorizedException(
        message = "장기 미접속으로 계정이 잠금처리 되었습니다.",
        errorCode = UnauthorizedErrorCode.INACTIVE_ACCOUNT,
    ) {
        private fun readResolve(): Any = DormantAccount
    }

    data object ExpiredAccountPassword : UnauthorizedException(
        message = "회원님의 비밀번호가 변경 된지 3개월이 지났습니다. 개인정보 보호를 위하여 비밀번호를 변경해주세요.",
        errorCode = UnauthorizedErrorCode.EXPIRED_ACCOUNT_PASSWORD,
    ) {
        private fun readResolve(): Any = ExpiredAccountPassword
    }

    data object InactiveAccount : UnauthorizedException(
        message = "비활성화된 계정입니다.",
        errorCode = UnauthorizedErrorCode.INACTIVE_ACCOUNT,
    ) {
        private fun readResolve(): Any = InactiveAccount
    }

    data object LoginAttemptsExceeded : UnauthorizedException(
        message = "로그인 시도 횟수를 초과하여 개인정보 보호를 위해 접속이 제한되었습니다. 비밀번호를 초기화해주세요.",
        errorCode = UnauthorizedErrorCode.LOGIN_ATTEMPTS_EXCEEDED,
    ) {
        private fun readResolve(): Any = LoginAttemptsExceeded
    }

    data object LockedAccount : UnauthorizedException(
        message = "로그인 시도 횟수를 초과하여 개인정보 보호를 위해 접속이 제한되었습니다. 비밀번호를 초기화해주세요.",
        errorCode = UnauthorizedErrorCode.LOCKED_ACCOUNT,
    ) {
        private fun readResolve(): Any = LockedAccount
    }

    // Not handled By Application
    data object UnknownError : UnauthorizedException(
        message = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다.",
        errorCode = UnauthorizedErrorCode.UNKNOWN_ERROR,
    ) {
        private fun readResolve(): Any = UnknownError
    }

    data class InternalError(
        override val message: String,
    ) : UnauthorizedException(
        message = message,
        errorCode = UnauthorizedErrorCode.INTERNAL_ERROR,
    )
}

fun getUnauthorizedError(authenticationException: AuthenticationException): UnauthorizedException {
    return when (authenticationException) {
        is UnauthorizedException -> authenticationException
        is BadCredentialsException -> UnauthorizedException.InvalidPassword
        is UsernameNotFoundException -> UnauthorizedException.AccountNotFound
        is AccountExpiredException -> UnauthorizedException.DormantAccount
        is CredentialsExpiredException -> UnauthorizedException.ExpiredAccountPassword
        is DisabledException -> UnauthorizedException.InactiveAccount
        is LockedException -> UnauthorizedException.LockedAccount

        is InternalAuthenticationServiceException,
        is AuthenticationServiceException,
        is ProviderNotFoundException,
        ->
            UnauthorizedException.InternalError(
                authenticationException.message ?: authenticationException.javaClass.simpleName,
            )

        else -> UnauthorizedException.UnknownError
    }
}
