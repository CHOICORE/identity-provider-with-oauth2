package me.choicore.likeapuppy.identityprovider.web.view

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class ConsentController(
	private val registeredClientRepository: RegisteredClientRepository,
	private val oauth2AuthorizationConsentService: OAuth2AuthorizationConsentService,
) {

	@GetMapping("/oauth2/consent")
	fun consent(
		principal: Principal,
		model: Model,
		@RequestParam(name = OAuth2ParameterNames.CLIENT_ID) clientId: String,
		@RequestParam(name = OAuth2ParameterNames.SCOPE) scope: String,
		@RequestParam(name = OAuth2ParameterNames.STATE) state: String,
		@RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) userCode: String?,
	): String {
		// Remove scopes that were already approved
		val scopesToApprove: MutableSet<String> = mutableSetOf()
		val previouslyApprovedScopes: MutableSet<String> = mutableSetOf()

		val registeredClient: RegisteredClient = checkNotNull(registeredClientRepository.findByClientId(clientId))

		val currentAuthorizationConsent =
			oauth2AuthorizationConsentService.findById(registeredClient.id, principal.name)

		val authorizedScopes: Set<String> = if (currentAuthorizationConsent != null) {
			currentAuthorizationConsent.getScopes()
		} else {
			emptySet()
		}
		for (requestedScope in StringUtils.delimitedListToStringArray(scope, " ")) {
			if (OidcScopes.OPENID == requestedScope) continue

			if (authorizedScopes.contains(requestedScope)) {
				previouslyApprovedScopes.add(requestedScope)
			} else {
				scopesToApprove.add(requestedScope)
			}
		}

		model.addAttribute("clientId", clientId)
		model.addAttribute("state", state)
		model.addAttribute("scopes", withDescription(scopesToApprove))
		model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes))
		model.addAttribute("principalName", principal.name)
		model.addAttribute("userCode", userCode)
		return "consent"
	}

	private fun withDescription(scopes: Set<String>): Set<ScopeWithDescription> {
		val scopeWithDescriptions: MutableSet<ScopeWithDescription> = HashSet()
		for (scope in scopes) {
			scopeWithDescriptions.add(ScopeWithDescription(scope))
		}
		return scopeWithDescriptions
	}

	class ScopeWithDescription internal constructor(val scope: String) {

		companion object {
			private val scopeDescriptions: MutableMap<String, String> = HashMap()

			init {
				scopeDescriptions[OidcScopes.PROFILE] =
					"This application will be able to read your profile information."
			}
		}
	}
}