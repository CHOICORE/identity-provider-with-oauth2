package me.choicore.likeapuppy.identityprovider.authentication

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler

class FormLoginAuthenticationFailureHandler(
    defaultFailureUrl: String,
) : ForwardAuthenticationFailureHandler(
    defaultFailureUrl,
) {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        super.onAuthenticationFailure(request, response, exception)
    }
}
