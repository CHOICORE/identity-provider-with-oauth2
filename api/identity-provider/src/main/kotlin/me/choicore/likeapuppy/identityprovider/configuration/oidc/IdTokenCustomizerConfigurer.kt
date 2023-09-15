package me.choicore.likeapuppy.identityprovider.oauth2.configuration

import me.choicore.likeapuppy.identityprovider.authentication.OidcUserInfoService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer

@Configuration
class IdTokenCustomizerConfigurer {

    @Bean
    fun oauth2TokenCustomizer(userInfoService: OidcUserInfoService): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer<JwtEncodingContext> { context: JwtEncodingContext ->
            if (OidcParameterNames.ID_TOKEN == context.tokenType.value) {
                val userInfo: OidcUserInfo = userInfoService.loadUser(context.getPrincipal<Authentication>().name)
                context.claims.claims { claims: MutableMap<String, Any> ->
                    claims.putAll(userInfo.claims)
                }
            }
        }
    }
}
