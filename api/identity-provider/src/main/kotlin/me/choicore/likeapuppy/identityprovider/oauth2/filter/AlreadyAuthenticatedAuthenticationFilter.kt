package me.choicore.likeapuppy.identityprovider.oauth2.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class AlreadyAuthenticatedAuthenticationFilter : OncePerRequestFilter() {

	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain,
	) {
		val securityContext: SecurityContext = SecurityContextHolder.getContext()

		if (
			isLoginRequest(request) &&
			securityContext.authentication != null &&
			securityContext.authentication.isAuthenticated
		) {
			response.sendRedirect("/")
			return
		}

		filterChain.doFilter(request, response)
	}

	private fun isLoginRequest(request: HttpServletRequest): Boolean {
		return "/login" == request.servletPath
	}
}