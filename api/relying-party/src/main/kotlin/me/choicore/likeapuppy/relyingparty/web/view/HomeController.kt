package me.choicore.likeapuppy.relyingparty.web.view

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping
    fun home(
        @AuthenticationPrincipal oidcUser: OidcUser,
        authentication: Authentication,
        model: Model,
    ): String {
        model.addAttribute("oidcUser", oidcUser)
        return "home"
    }
}
