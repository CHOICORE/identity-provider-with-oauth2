package me.choicore.likeapuppy.identityprovider.web.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {

	@GetMapping("/login")
	fun login(): String {
		return "login"
	}

	@GetMapping
	fun home(): String {
		return "home"
	}
}