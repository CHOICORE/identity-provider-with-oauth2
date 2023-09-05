package me.choicore.likeapuppy.identityprovider.oauth2.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import java.time.Duration
import java.util.UUID

@Configuration
class AuthorizationConfigurer {

	@Bean
	@Order(1)
	fun authorizationServerSecurityFilterChain(
		httpSecurity: HttpSecurity,
		jwtDecoder: JwtDecoder,
	): SecurityFilterChain {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity)
		httpSecurity
			.getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
			.oidc(Customizer.withDefaults())

		httpSecurity {
			oauth2ResourceServer {
				jwt {}
			}
			exceptionHandling {
				defaultAuthenticationEntryPointFor(
					LoginUrlAuthenticationEntryPoint("/login"),
					MediaTypeRequestMatcher(MediaType.TEXT_HTML),
				)
			}
		}

		return httpSecurity.build()
	}

	@Bean
	fun oauth2AuthorizationConsentService(): OAuth2AuthorizationConsentService {
		val oauth2AuthorizationConsent: OAuth2AuthorizationConsent =
			OAuth2AuthorizationConsent.withId("like-a-puppy", "1")
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.build()
		return InMemoryOAuth2AuthorizationConsentService(oauth2AuthorizationConsent)
	}

	@Bean
	fun registeredClientRepository(): RegisteredClientRepository {
		val registeredClient: RegisteredClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("like-a-puppy")
			.clientSecret("{noop}like-a-puppy")
			.clientAuthenticationMethods { clientAuthenticationMethod: MutableSet<ClientAuthenticationMethod> ->
				clientAuthenticationMethod.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				clientAuthenticationMethod.add(ClientAuthenticationMethod.CLIENT_SECRET_POST)
			}
			.authorizationGrantTypes { authorizationGrantType: MutableSet<AuthorizationGrantType> ->
				authorizationGrantType.add(AuthorizationGrantType.AUTHORIZATION_CODE)
				authorizationGrantType.add(AuthorizationGrantType.CLIENT_CREDENTIALS)
				authorizationGrantType.add(AuthorizationGrantType.REFRESH_TOKEN)
				authorizationGrantType.add(AuthorizationGrantType.JWT_BEARER)
			}
			.redirectUris { redirectUri ->
				redirectUri.add("http://127.0.0.1:8080/v1/oauth2/authorize")
			}
			.scope(OidcScopes.OPENID)
			.scope(OidcScopes.PROFILE)
			.scope("USER")
			.tokenSettings(
				Builder.tokenSettings {
					accessTokenTimeToLive(Duration.ofHours(2))
					refreshTokenTimeToLive(Duration.ofDays(7))
				},
			)
			.clientSettings(
				Builder.clientSettings {
					requireAuthorizationConsent(false)
				},
			)
			.build()
		return InMemoryRegisteredClientRepository(registeredClient)
	}

	@Bean
	fun authorizationService(): OAuth2AuthorizationService = InMemoryOAuth2AuthorizationService()

	@Bean
	fun authorizationServerSettings(): AuthorizationServerSettings {
		return AuthorizationServerSettings
			.builder()
			.tokenEndpoint("/v1/oauth2/token")
			.build()
	}
}

object Builder {

	fun tokenSettings(block: TokenSettings.Builder.() -> Unit): TokenSettings {
		return TokenSettings.builder().apply(block).build()
	}

	fun clientSettings(block: ClientSettings.Builder.() -> Unit): ClientSettings {
		return ClientSettings.builder().apply(block).build()
	}
}