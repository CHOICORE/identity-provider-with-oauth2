package me.choicore.likeapuppy.identityprovider.configuration

import me.choicore.likeapuppy.identityprovider.authentication.FormLoginAuthenticationFailureHandler
import me.choicore.likeapuppy.identityprovider.authentication.FormLoginAuthenticationSuccessHandler
import org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console
import org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity(debug = true)
@Configuration
class DefaultSecurityConfigurer {

    @Bean
    @Order(2)
    fun defaultSecurityFilterChain(
        httpSecurity: HttpSecurity,
    ): SecurityFilterChain {
        httpSecurity {
            formLogin {
                loginPage = "/login"
                authenticationSuccessHandler = FormLoginAuthenticationSuccessHandler()
                authenticationFailureHandler = FormLoginAuthenticationFailureHandler()
                permitAll()
            }
            logout {
                logoutRequestMatcher = AntPathRequestMatcher("/logout")
                permitAll()
            }
            authorizeHttpRequests {
                authorize(matches = anyRequest, access = authenticated)
            }
        }
        return httpSecurity.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer = WebSecurityCustomizer { webSecurity: WebSecurity ->
        webSecurity.ignoring().requestMatchers(
            AntPathRequestMatcher("/assets/**"),
            AntPathRequestMatcher("/error/**"),
            toStaticResources().atCommonLocations(),
            toH2Console(),
        )
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher?): AuthenticationEventPublisher {
        return DefaultAuthenticationEventPublisher(applicationEventPublisher)
    }
}