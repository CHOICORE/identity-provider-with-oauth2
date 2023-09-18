package me.choicore.likeapuppy.relyingparty.web.view

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping
    fun home(authentication: Authentication, model: Model): String {
        println(authentication)
        return "home"
    }
}
