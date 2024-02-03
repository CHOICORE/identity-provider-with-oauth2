package me.choicore.likeapuppy.identityprovider

import com.gargoylesoftware.htmlunit.Page
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.WebResponse
import com.gargoylesoftware.htmlunit.html.HtmlButton
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestConstructor
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class IdentityProviderApplicationTests(
    val webClient: WebClient,
) {

    companion object {
        private const val REDIRECT_URI = "http://127.0.0.1:8080/login/oauth2/code/like-a-puppy-developer-client"
        private const val AUTHORIZATION_URI = "/oauth2/authorize"
        private val AUTHORIZATION_REQUEST: String = UriComponentsBuilder
            .fromPath(AUTHORIZATION_URI)
            .queryParam("response_type", "code")
            .queryParam("client_id", "like-a-puppy")
            .queryParam("client_secret", "like-a-puppy-secret")
            .queryParam("scope", "openid")
            .queryParam("state", "state")
            .queryParam("redirect_uri", REDIRECT_URI)
            .toUriString()

        @Throws(IOException::class)
        private fun <P : Page> login(
            page: HtmlPage,
            username: String,
            password: String,
        ): P {
            val usernameInput: HtmlInput = page.querySelector("input[name=\"username\"]")
            val passwordInput: HtmlInput = page.querySelector("input[name=\"password\"]")
            val loginButton: HtmlButton = page.querySelector("button")
            usernameInput.type(username)
            passwordInput.type(password)
            return loginButton.click()
        }

        private fun assertLoginPage(page: HtmlPage) {
            Assertions.assertThat(page.url.toString()).endsWith("/login")
            val usernameInput: HtmlInput = page.querySelector("input[name=\"username\"]")
            val passwordInput: HtmlInput = page.querySelector("input[name=\"password\"]")
            val loginButton: HtmlButton = page.querySelector("button")
            Assertions.assertThat(usernameInput).isNotNull()
            Assertions.assertThat(passwordInput).isNotNull()
            Assertions.assertThat(loginButton.textContent).isEqualTo("로그인")
        }
    }

    @BeforeEach
    fun setUp() {
        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.options.isRedirectEnabled = true
        webClient.cookieManager.clearCookies()
    }

    @Test
    @DisplayName("로그인 성공 시 200 코드 반환")
    @Throws(IOException::class)
    fun whenLoginSuccessfulThenHttpStatusOK() {
        val htmlPage: HtmlPage = webClient.getPage("/")

        assertLoginPage(htmlPage)

        val webResponse: WebResponse = login<HtmlPage>(htmlPage, "1", "1").webResponse

        Assertions.assertThat(webResponse.statusCode).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("로그인 되지 않은 상태로 인증 요청 시 로그인 페이지로 리다이렉트")
    @Throws(IOException::class)
    fun whenNotLoggedInAndRequestingTokenThenRedirectsToLogin() {
        assertLoginPage(webClient.getPage(AUTHORIZATION_REQUEST))
    }
}
