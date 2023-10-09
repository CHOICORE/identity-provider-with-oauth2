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
        return findAuthoritiesAndValidate(
            authorityRepository::findByNames,
            authorityNames,
            "Invalid authorities: $authorityNames",
        )
    }

    fun getAuthorityByIds(authorityIds: List<Long>): List<Authority> {
        return findAuthoritiesAndValidate(
            function = authorityRepository::findByIds,
            parameter = authorityIds,
            errorMessage = "Invalid authorities: $authorityIds",
        )
    }

    fun validateAuthorityIds(authorityIds: List<Long>) {
        findAuthoritiesAndValidate(
            authorityRepository::findByIds,
            authorityIds,
            errorMessage = "Invalid authorities: $authorityIds",
        )
    }

    private inline fun <T> findAuthoritiesAndValidate(
        function: (List<T>) -> List<Authority>,
        parameter: List<T>,
        errorMessage: String,
    ): List<Authority> {
        val authorities = function(parameter)
        validateSizeMatch(authorities.size, parameter.size, errorMessage)
        return authorities
    }

    private fun validateSizeMatch(actualSize: Int, expectedSize: Int, errorMessage: String) {
        if (actualSize != expectedSize) {
            throw IllegalArgumentException(errorMessage)
        }
    }
}
