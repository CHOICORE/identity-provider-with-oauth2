package me.choicore.likeapuppy.identityprovider

import com.nimbusds.jose.util.JSONObjectUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AuthorizationGrantTypeTests(
	private val mockMvc: MockMvc,
	private var registeredClientRepository: RegisteredClientRepository,
) {

	companion object {
		private const val REDIRECT_URI = "http://127.0.0.1:8080/test"
		private const val TOKEN_ENDPOINT = "/oauth2/token"
		private const val AUTHORIZATION_URI = "/oauth2/authorize"

		private val AUTHORIZATION_REQUEST: String = UriComponentsBuilder
			.fromPath(AUTHORIZATION_URI)
			.queryParam("response_type", "code")
			.queryParam("client_id", "like-a-puppy")
			.queryParam("client_secret", "like-a-puppy")
			.queryParam("scope", "openid")
			.queryParam("state", "state")
			.queryParam("redirect_uri", REDIRECT_URI)
			.toUriString()
	}

	@Test
	@DisplayName("grant_type=authorization_code 을 통해 사용자에게 인증 토큰을 발급한다.")
	@WithMockUser(username = "1", password = "1", roles = ["USER"])
	fun whenUserRequestAuthorizationThenGetAccessTokenWithGrantedAuthorizationCode() {
		val mvcResult: MvcResult = mockMvc.get(AUTHORIZATION_REQUEST)
			.andExpect {
				status { is3xxRedirection() }
			}.andReturn()

		val redirectedUrl: String = checkNotNull(mvcResult.response.redirectedUrl)

		assertThat(redirectedUrl).startsWith(REDIRECT_URI)

		val queryParams: MultiValueMap<String, String> =
			UriComponentsBuilder.fromUriString(redirectedUrl).build().queryParams
		val code: String = checkNotNull(queryParams.getFirst("code"))

		val authorizationUri = UriComponentsBuilder
			.fromPath(TOKEN_ENDPOINT)
			.queryParam("grant_type", "authorization_code")
			.queryParam("code", code)
			.queryParam("client_id", "like-a-puppy")
			.queryParam("client_secret", "like-a-puppy")
			.queryParam("scope", "openid")
			.queryParam("redirect_uri", REDIRECT_URI)
			.toUriString()

		mockMvc.post(authorizationUri)
			.andExpect {
				status { isOk() }
				content {
					contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
				}
			}
			.andDo { print() }
	}

	@Test
	@DisplayName("grant_type=client_credentials 을 통해 사용자에게 인증 토큰을 발급한다.")
	@WithMockUser(username = "1", password = "1", roles = ["USER"])
	fun whenUserRequestAuthorizationThenGetAccessTokenWithGrantedClientCredentials() {
		val clientId: Pair<String, String> = Pair("client_id", "like-a-puppy")
		val clientSecret: Pair<String, String> = Pair("client_secret", "like-a-puppy")

		mockMvc.post(TOKEN_ENDPOINT) {
			headers {
				HttpHeaders().setBasicAuth(clientId.second, clientSecret.second)
			}
			param("grant_type", "client_credentials")
			param(clientId.first, clientId.second)
			param(clientSecret.first, clientSecret.second)
			param("scope", "openid")
			param("state", "state")
		}.andExpect {
			status { isOk() }
			content {
				contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
				jsonPath("\$.access_token") { isNotEmpty() }
				jsonPath("\$.token_type") { value("Bearer") }
				jsonPath("\$.expires_in") { isNumber() }
			}
		}
	}

	@Test
	@DisplayName("OpenID Connect 을 통해 사용자 정보를 조회한다.")
	@WithMockUser(username = "1")
	fun whenRequestOidcAuthorizationThenGetIdTokenForOpenIdConnect() {
		val clientId: Pair<String, String> = Pair("client_id", "like-a-puppy")
		val clientSecret: Pair<String, String> = Pair("client_secret", "like-a-puppy")

		// 1. Authorization Code Flow
		val getAuthorizationCodeResult: MvcResult = mockMvc
			.get(AUTHORIZATION_URI) {
				param("response_type", "code")
				param(clientId.first, clientId.second)
				param(clientSecret.first, clientSecret.second)
				param("scope", "openid")
				param("state", "state")
				param("redirect_uri", REDIRECT_URI)
			}
			.andExpect {
				status { is3xxRedirection() }
			}
			.andReturn()

		val redirectedUrl: String = checkNotNull(getAuthorizationCodeResult.response.redirectedUrl)

		assertThat(redirectedUrl).startsWith(REDIRECT_URI)

		val queryParams: MultiValueMap<String, String> =
			UriComponentsBuilder.fromUriString(redirectedUrl).build().queryParams
		// 2. Get Issued Token
		val getIssuedTokenResult = mockMvc
			.post(TOKEN_ENDPOINT) {
				param("grant_type", "authorization_code")
				param("code", checkNotNull(queryParams.getFirst("code")))
				param(clientId.first, clientId.second)
				param(clientSecret.first, clientSecret.second)
				param("scope", "openid")
				param("redirect_uri", REDIRECT_URI)
			}
			.andExpect {
				status { isOk() }
				content {
					contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
					jsonPath("\$.id_token") { isNotEmpty() }
				}
			}
			.andDo { print() }
			.andReturn()

		val jsonObject: MutableMap<String, Any> = JSONObjectUtils
			.parse(getIssuedTokenResult.response.contentAsString)

		val authorizationHeader = "${jsonObject["token_type"]} ${jsonObject["access_token"]}"

		// 3. Get User Info
		mockMvc
			.get("/userinfo") {
				header(HttpHeaders.AUTHORIZATION, authorizationHeader)
			}
			.andExpect {
				status { isOk() }
				content {
					contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
					jsonPath("\$.sub") { value("1") }
				}
			}
	}

	@Test
	@DisplayName("Access Token 을 통해 사용자 정보를 조회한다.")
	@WithMockUser(username = "1")
	fun whenRequestAuthorizationThenGetUserInfoWithAccessToken() {
		val clientId: Pair<String, String> = Pair("client_id", "like-a-puppy")
		val clientSecret: Pair<String, String> = Pair("client_secret", "like-a-puppy")

		// 1. Authorization Code Flow
		val getAuthorizationCodeResult: MvcResult = mockMvc
			.get(AUTHORIZATION_URI) {
				param("response_type", "code")
				param(clientId.first, clientId.second)
				param(clientSecret.first, clientSecret.second)
				param("scope", "openid")
				param("state", "state")
				param("redirect_uri", REDIRECT_URI)
			}
			.andExpect {
				status { is3xxRedirection() }
			}
			.andReturn()

		val redirectedUrl: String = checkNotNull(getAuthorizationCodeResult.response.redirectedUrl)

		assertThat(redirectedUrl).startsWith(REDIRECT_URI)

		val queryParams: MultiValueMap<String, String> =
			UriComponentsBuilder.fromUriString(redirectedUrl).build().queryParams

		// 2. Get Issued Token
		val getIssuedTokenResult =
			mockMvc.post(TOKEN_ENDPOINT) {
				param("grant_type", "authorization_code")
				param("code", checkNotNull(queryParams.getFirst("code")))
				param(clientId.first, clientId.second)
				param(clientSecret.first, clientSecret.second)
				param("scope", "openid")
				param("redirect_uri", REDIRECT_URI)
			}.andExpect {
				status { isOk() }
				content {
					contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
					jsonPath("\$.access_token") { isNotEmpty() }
				}
			}
				.andDo { print() }.andReturn()

		val jsonObject: MutableMap<String, Any> = JSONObjectUtils
			.parse(getIssuedTokenResult.response.contentAsString)

		val authorizationHeader = "${jsonObject["token_type"]} ${jsonObject["access_token"]}"

		// 3. Get User Info
		mockMvc.get("/userinfo") {
			header(HttpHeaders.AUTHORIZATION, authorizationHeader)
		}.andExpect {
			status { isOk() }
			content {
				contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)
				jsonPath("\$.sub") { value("1") }
			}
		}
	}
}