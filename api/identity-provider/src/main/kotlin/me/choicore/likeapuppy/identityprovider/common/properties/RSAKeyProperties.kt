package me.choicore.likeapuppy.identityprovider.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@ConfigurationProperties(prefix = "rsa")
data class RSAKeyProperties(
    val publicKey: RSAPublicKey,
    val privateKey: RSAPrivateKey,
)
