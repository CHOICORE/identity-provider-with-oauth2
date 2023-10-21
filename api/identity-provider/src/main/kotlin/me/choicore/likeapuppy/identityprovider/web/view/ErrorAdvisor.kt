package me.choicore.likeapuppy.identityprovider.web.view

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ErrorAdvisor : ErrorController {
    @RequestMapping("/error")
    fun handleError(
        model: Model,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): String {
        return "/error"
    }
}
