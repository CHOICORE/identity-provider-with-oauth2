package me.choicore.likeapuppy.identityprovider

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.security.crypto.keygen.StringKeyGenerator
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

private val DEFAULT_STATE_GENERATOR: StringKeyGenerator = Base64StringKeyGenerator(
    Base64.getUrlEncoder(),
)

private val DEFAULT_SECURE_KEY_GENERATOR: StringKeyGenerator = Base64StringKeyGenerator(
    Base64.getUrlEncoder()
        .withoutPadding(),
    96,
)

private fun createHash(
    value: String,
): String {
    val digest: ByteArray = MessageDigest.getInstance("SHA-256")
        .digest(value.toByteArray(StandardCharsets.US_ASCII))
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
}

fun generateCodeChallenge(): CodeChallenge {
    val codeVerifier: String = DEFAULT_SECURE_KEY_GENERATOR.generateKey()
    val codeChallenge: String = createHash(codeVerifier)
    return CodeChallenge(codeVerifier, codeChallenge)
}

data class CodeChallenge(
    val codeVerifier: String,
    val codeChallenge: String,
    val codeChallengeMethod: String = "S256",
)

fun generateState(): Pair<String, String> {
    val state: String = DEFAULT_STATE_GENERATOR.generateKey()
    val stateHash: String = createHash(state)
    return Pair("state", stateHash)
}

fun generateNonce(): Pair<String, String> {
    val nonce: String = DEFAULT_SECURE_KEY_GENERATOR.generateKey()
    val nonceHash: String = createHash(nonce)
    return Pair(OidcParameterNames.NONCE, nonceHash)
}
