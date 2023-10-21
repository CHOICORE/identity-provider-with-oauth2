package me.choicore.likeapuppy.identityprovider.configuration

import me.choicore.likeapuppy.core.domain.user.repository.AccountQueryRepository
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountCommandProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountQueryProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
}
