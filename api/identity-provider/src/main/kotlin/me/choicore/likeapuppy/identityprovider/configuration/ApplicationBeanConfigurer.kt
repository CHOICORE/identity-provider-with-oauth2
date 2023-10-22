package me.choicore.likeapuppy.identityprovider.configuration

import me.choicore.likeapuppy.core.common.Encryptor
import me.choicore.likeapuppy.core.domain.user.repository.AccountQueryRepository
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountCommandProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountQueryProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration(proxyBeanMethods = false)
class ApplicationBeanConfigurer {
    @Bean
    fun accountQueryProcessor(repository: AccountQueryRepository): AccountQueryProcessor {
        return AccountQueryProcessor(repository)
    }

    @Bean
    fun accountCommandProcessor(repository: UserCommandRepository): AccountCommandProcessor {
        return AccountCommandProcessor(repository)
    }

    /**
     * This bean is used to encrypt the password.
     */
    @Bean
    fun encrypt(passwordEncoder: PasswordEncoder): Encryptor = object : Encryptor {
        override fun encode(rawPassword: String): String {
            return passwordEncoder.encode(rawPassword)
        }
    }
}
