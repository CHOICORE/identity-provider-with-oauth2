package me.choicore.likeapuppy.identityprovider.authentication

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

class FormLoginAuthenticationSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        super.onAuthenticationSuccess(request, response, authentication)
    }
}
