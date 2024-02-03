package me.choicore.likeapuppy.identityprovider

import me.choicore.likeapuppy.core.authorization.jpa.service.JpaRegisteredClientRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class RegistrationClientInitializer(
    private val registeredClientRepository: JpaRegisteredClientRepository,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val encodedSecret = passwordEncoder.encode("like-a-puppy-secret")
        println("encoded >>>>>>>>> $encodedSecret")
        val registeredClient: RegisteredClient =
            RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("like-a-puppy")
                .clientSecret(encodedSecret)
                .clientAuthenticationMethods { clientAuthenticationMethod: MutableSet<ClientAuthenticationMethod> ->
                    clientAuthenticationMethod.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    clientAuthenticationMethod.add(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                }
                .authorizationGrantTypes { authorizationGrantType: MutableSet<AuthorizationGrantType> ->
                    authorizationGrantType.add(AuthorizationGrantType.AUTHORIZATION_CODE)
                    authorizationGrantType.add(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    authorizationGrantType.add(AuthorizationGrantType.REFRESH_TOKEN)
                }
                .redirectUris { redirectUri ->
                    redirectUri.add("http://127.0.0.1:8080/login/oauth2/code/like-a-puppy-developer-client")
                    redirectUri.add("http://localhost:8080/login/oauth2/code/like-a-puppy-developer-client")
                }
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .tokenSettings(
                    Builder.tokenSettings {
                        accessTokenTimeToLive(Duration.ofHours(2))
                        refreshTokenTimeToLive(Duration.ofDays(7))
                    },
                )
                .clientSettings(
                    Builder.clientSettings {
                        requireAuthorizationConsent(true)
//                        requireProofKey(true)
                    },
                )
                .build()
        registeredClientRepository.save(registeredClient)
    }

    private object Builder {
        fun tokenSettings(block: TokenSettings.Builder.() -> Unit): TokenSettings {
            return TokenSettings.builder().apply(block).build()
        }

        fun clientSettings(block: ClientSettings.Builder.() -> Unit): ClientSettings {
            return ClientSettings.builder().apply(block).build()
        }
    }
}
