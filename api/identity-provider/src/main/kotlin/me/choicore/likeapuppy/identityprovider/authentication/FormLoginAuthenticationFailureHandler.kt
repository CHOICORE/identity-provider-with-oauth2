package me.choicore.likeapuppy.identityprovider.authentication

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.choicore.likeapuppy.identityprovider.exception.UNAUTHORIZED_ERROR
import me.choicore.likeapuppy.identityprovider.exception.getUnauthorizedError
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class FormLoginAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        request.setAttribute(UNAUTHORIZED_ERROR, getUnauthorizedError(exception))
        super.onAuthenticationFailure(request, response, exception)
    }
}
