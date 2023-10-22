package me.choicore.likeapuppy.identityprovider

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
class AuthorizationTests(
    private val mockMvc: MockMvc,
) {
    companion object {
        private const val REDIRECT_URI = "http://127.0.0.1:8080/login/oauth2/code/like-a-puppy-developer-client"
        private val codeChallenge: CodeChallenge = generateCodeChallenge()
        private val state: Pair<String, String> = generateState()
        private val nonce: Pair<String, String> = generateNonce()
    }

    fun grantedAuthorizationCode(): String {
        val mvcResult: MvcResult =
            mockMvc.get("/oauth2/authorize") {
                param("response_type", "code")
                param("client_id", "like-a-puppy")
                param("client_secret", "like-a-puppy-secret")
                param("scope", "openid")
                param(state.first, state.second)
                param(nonce.first, nonce.second)
                param("redirect_uri", REDIRECT_URI)
                param("code_challenge", codeChallenge.codeChallenge)
                param("code_challenge_method", codeChallenge.codeChallengeMethod)
            }.andExpect {
                status { is3xxRedirection() }
                redirectedUrlPattern(REDIRECT_URI + "?code=*&state=" + state.second)
            }.andReturn()

        val redirectedUrl: String = checkNotNull(mvcResult.response.redirectedUrl)
        val queryParams: MultiValueMap<String, String> =
            UriComponentsBuilder.fromUriString(redirectedUrl).build().queryParams
        return checkNotNull(queryParams.getFirst("code"))
    }

    @Test
    @DisplayName("grant_type=authorization_code 을 통해 사용자에게 토큰을 발급한다.")
    @WithMockUser(username = "1")
    fun whenUserRequestAuthorizationCodeByAuthenticationThenTokenIsIssued() {
        val code: String = grantedAuthorizationCode()
        val clientId: Pair<String, String> = Pair("client_id", "like-a-puppy")
        val clientSecret: Pair<String, String> = Pair("client_secret", "like-a-puppy-secret")
        mockMvc.post("/oauth2/token") {
            headers { setBasicAuth(clientId.second, clientSecret.second) }
            param("scope", "openid")
            param("grant_type", "authorization_code")
            param("code", code)
            param("redirect_uri", REDIRECT_URI)
            param("code_verifier", codeChallenge.codeVerifier)
        }.andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
                jsonPath("\$.id_token") { isNotEmpty() }
                jsonPath("\$.access_token") { isNotEmpty() }
                jsonPath("\$.refresh_token") { isNotEmpty() }
            }
        }
    }
}
