package me.choicore.likeapuppy.identityprovider.web.view

import jakarta.servlet.http.HttpServletRequest
import me.choicore.likeapuppy.identityprovider.common.Slf4j
import me.choicore.likeapuppy.identityprovider.exception.UNAUTHORIZED_ERROR
import me.choicore.likeapuppy.identityprovider.exception.getUnauthorizedError
import org.slf4j.Logger
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.WebAttributes
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class LoginController {
    private val logger: Logger = Slf4j

    @GetMapping
    fun home(): String = "index"

    @GetMapping("/login")
    fun goingToLoginPage(): String = "login"

    @PostMapping("/login-error")
    fun redirectToLoginPageWithErrorMessage(
        request: HttpServletRequest,
        redirectAttributes: RedirectAttributes,
    ): String {
        logger.info("Login attempt failed. Redirecting to the login page with an accompanying error message.")
        request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)?.let { authenticationException: Any ->
            when (authenticationException) {
                is AuthenticationException -> {
                    redirectAttributes.addFlashAttribute(
                        UNAUTHORIZED_ERROR,
                        getUnauthorizedError(authenticationException),
                    )
                }

                else -> {
                    logger.error("Unknown attributes: $authenticationException")
                }
            }
        }
        return "redirect:/login"
    }
}
