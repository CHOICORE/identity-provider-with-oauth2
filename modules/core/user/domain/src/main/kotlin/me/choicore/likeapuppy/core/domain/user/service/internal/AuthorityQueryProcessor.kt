package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.Authority
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.repository.AuthorityQueryRepository

class AuthorityQueryProcessor(
    private val authorityRepository: AuthorityQueryRepository,
) {
    fun getAuthorityById(id: Long): Authority {
        return authorityRepository.findById(id)
    }

    fun getAuthorityByAuthorityNames(authorityNames: List<AuthorityNames>): List<Authority> {
        return fetch(
            function = authorityRepository::findByNames,
            parameter = authorityNames,
        )
    }

    fun getAuthorityByIds(authorityIds: List<Long>): List<Authority> {
        return fetch(
            function = authorityRepository::findByIds,
            parameter = authorityIds,
        )
    }

    fun validateAuthorityIds(authorityIds: List<Long>) {
        fetch(
            function = authorityRepository::findByIds,
            parameter = authorityIds,
        )
    }

    private inline fun <T> fetch(
        function: (List<T>) -> List<Authority>,
        parameter: List<T>,
    ): List<Authority> {
        val authorities: List<Authority> = function(parameter)

        validate(authorities, parameter)

        return authorities
    }

    private fun <T> validate(
        authorities: List<Authority>,
        parameter: List<T>,
    ) {
        if (authorities.size != parameter.size) {
            throw IllegalArgumentException(getErrorMessage(parameter, authorities))
        }
    }

    private fun <T> getErrorMessage(
        parameter: List<T>,
        authorities: List<Authority>,
    ): String {
        val parameterType: String = when (parameter.firstOrNull()) {
            is Long -> "Authority IDs"
            is AuthorityNames -> "Authority names"
            else -> "Parameters"
        }
        return "Invalid $parameterType provided: $parameter. Expected ${parameter.size} authorities but found ${authorities.size}."
    }
}
