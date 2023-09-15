package me.choicore.likeapuppy.identityprovider.web.view

import jakarta.servlet.http.HttpServletRequest
import me.choicore.likeapuppy.identityprovider.exception.UNAUTHORIZED_ERROR
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ErrorAdvisor : ErrorController {

    @RequestMapping("/error")
    fun handleError(model: Model, request: HttpServletRequest): String {
        model.addAttribute("errorMessage", request.getAttribute(UNAUTHORIZED_ERROR))
        return "/login"
    }
}
