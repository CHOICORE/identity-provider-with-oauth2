package me.choicore.likeapuppy.identityprovider.oauth2.configuration

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import me.choicore.likeapuppy.identityprovider.common.properties.RSAKeyProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID

@Configuration(proxyBeanMethods = false)
class JwtConfigurer(
    private val rsaKeyProperties: RSAKeyProperties,
) {

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val rsaPublicKey: RSAPublicKey = rsaKeyProperties.publicKey
        val rsaPrivateKey: RSAPrivateKey = rsaKeyProperties.privateKey
        val rsaKey: RSAKey = RSAKey.Builder(rsaPublicKey)
            .privateKey(rsaPrivateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }
}
