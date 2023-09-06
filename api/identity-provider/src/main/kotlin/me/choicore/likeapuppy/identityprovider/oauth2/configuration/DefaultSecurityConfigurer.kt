package me.choicore.likeapuppy.identityprovider.oauth2.configuration

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity(debug = true)
@Configuration
class DefaultSecurityConfigurer {

	@Bean
	fun securityFilterChain(
		httpSecurity: HttpSecurity,
	): SecurityFilterChain {
		httpSecurity {
			formLogin {
				loginPage = "/login"
				permitAll()
			}
			logout {
				logoutRequestMatcher = AntPathRequestMatcher("/logout")
				permitAll()
			}
// 			addFilterBefore<UsernamePasswordAuthenticationFilter>(AlreadyAuthenticatedAuthenticationFilter())
			authorizeHttpRequests {
				authorize(matches = anyRequest, access = authenticated)
			}
		}
		return httpSecurity.build()
	}

	@Bean
	fun webSecurityCustomizer(): WebSecurityCustomizer = WebSecurityCustomizer { webSecurity: WebSecurity ->
		webSecurity
			.ignoring()
			.requestMatchers(
				AntPathRequestMatcher("/assets/**"),
				PathRequest.toStaticResources().atCommonLocations(),
			)
	}

	@Bean
	fun users(): UserDetailsService {
		val userDetails: UserDetails = User.withDefaultPasswordEncoder()
			.username("1")
			.password("1")
			.roles("USER")
			.build()

		return InMemoryUserDetailsManager(userDetails)
	}
}