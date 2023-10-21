package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.Authority
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.repository.AuthorityQueryRepository

class AuthorityQueryProcessor(
    private val repository: AuthorityQueryRepository,
) {
    fun getAuthorityById(id: Long): Authority {
        return fetch(
            execute = repository::findById,
            parameter = id,
        )
    }

    fun getAuthorityByIds(authorityIds: List<Long>): List<Authority> {
        return fetch(
            execute = repository::findByIds,
            parameter = authorityIds,
        )
    }

    fun getAuthorityByAuthorityNames(authorityNames: List<AuthorityNames>): List<Authority> {
        return fetch(
            execute = repository::findByNames,
            parameter = authorityNames,
        )
    }

    fun validateAuthorityIds(authorityIds: List<Long>) {
        fetch(
            execute = repository::findByIds,
            parameter = authorityIds,
        )
    }
}

private fun <T> getErrorMessage(
    parameter: List<T>,
    authorities: List<Authority>,
): String {
    val parameterType: String =
        when (parameter.firstOrNull()) {
            is Long -> "Authority IDs"
            is AuthorityNames -> "Authority names"
            else -> "Parameters"
        }
    return "Invalid $parameterType provided: $parameter. Expected ${parameter.size} authorities but found ${authorities.size}."
}

private inline fun <T> fetch(
    execute: (T) -> Authority,
    parameter: T,
): Authority {
    return execute(parameter)
}

private inline fun <T> fetch(
    execute: (List<T>) -> List<Authority>,
    parameter: List<T>,
): List<Authority> {
    return execute(parameter)
        .apply {
            validate(authorities = this, parameter = parameter)
        }
}

private fun <T> validate(
    authorities: List<Authority>,
    parameter: List<T>,
) {
    if (authorities.size != parameter.size) {
        throw IllegalArgumentException(getErrorMessage(parameter, authorities))
    }
}
