package me.choicore.likeapuppy.identityprovider.configuration.annotation

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Retention(value = AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@EntityScan
@EnableJpaRepositories
annotation class EnableJpaConfiguration
