package me.choicore.likeapuppy.relyingparty.configuration.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration(proxyBeanMethods = false)
class OAuth2ClientWithPkceConfigurer {
    @Bean
    fun securityWebFilterChain(
        serverHttpSecurity: ServerHttpSecurity,
        httpSecurity: ServerHttpSecurity,
        serverOAuth2AuthorizationRequestResolver: ServerOAuth2AuthorizationRequestResolver,
    ): SecurityWebFilterChain {
        serverHttpSecurity.formLogin { formLogin: ServerHttpSecurity.FormLoginSpec -> formLogin.disable() }
        serverHttpSecurity.oauth2Login { oauth2LoginSpec: ServerHttpSecurity.OAuth2LoginSpec ->
            oauth2LoginSpec.authorizationRequestResolver(serverOAuth2AuthorizationRequestResolver)
        }
        serverHttpSecurity.authorizeExchange { authorizeExchangeSpec: ServerHttpSecurity.AuthorizeExchangeSpec ->
            authorizeExchangeSpec.anyExchange().authenticated()
        }
        return serverHttpSecurity.build()
    }

    @Bean
    fun serverOAuth2AuthorizationRequestPkceResolver(
        reactiveClientRegistrationRepository: ReactiveClientRegistrationRepository,
    ): ServerOAuth2AuthorizationRequestResolver {
        return DefaultServerOAuth2AuthorizationRequestResolver(reactiveClientRegistrationRepository)
            .apply {
                setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce())
            }
    }
}
