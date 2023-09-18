package me.choicore.likeapuppy.identityprovider.web.view

import jakarta.servlet.http.HttpServletRequest
import me.choicore.likeapuppy.identityprovider.exception.UNAUTHORIZED_ERROR
import me.choicore.likeapuppy.identityprovider.exception.getUnauthorizedError
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.WebAttributes
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class LoginController {

    @GetMapping("/login")
    fun goingToLoginPage(
        request: HttpServletRequest,
        model: Model,
    ): String {
        return "login"
    }

    @PostMapping("/login-error")
    fun redirectToLoginPageWithErrorMessage(
        request: HttpServletRequest,
        redirectAttributes: RedirectAttributes,
    ): String {
        request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)?.let {
            redirectAttributes.addFlashAttribute(
                UNAUTHORIZED_ERROR,
                getUnauthorizedError(it as AuthenticationException),
            )
        }
        return "redirect:/login"
    }

    @GetMapping
    fun home(): String {
        return "index"
    }
}
