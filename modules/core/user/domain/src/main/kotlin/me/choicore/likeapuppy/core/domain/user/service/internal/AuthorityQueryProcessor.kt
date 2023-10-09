package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.Authority
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.repository.AuthorityQueryRepository

class AuthorityQueryProcessor(
    private val authorityRepository: AuthorityQueryRepository,
) {
    fun getAuthority(id: Long): Authority {
        return authorityRepository.findById(id)
    }

    fun getAuthorities(authorityNames: List<AuthorityNames>): List<Authority> {
        val authorities: List<Authority> = authorityRepository.findByNames(authorityNames)
        validate(authorities.size, authorityNames.size, "Invalid authorities: $authorityNames")
        return authorities
    }

    fun getAuthorities(authorityIds: List<Long>): List<Authority> {
        val authorities: List<Authority> = authorityRepository.findByIds(authorityIds)
        validate(authorities.size, authorityIds.size, "Invalid authorities: $authorityIds")
        return authorities
    }

    fun validateAuthorityIds(authorityIds: List<Long>) {
        val authorities: List<Authority> = authorityRepository.findByIds(authorityIds)
        validate(authorities.size, authorityIds.size, "Invalid authorities: $authorityIds")
    }

    private fun validate(actualSize: Int, expectedSize: Int, errorMessage: String) {
        if (actualSize != expectedSize) {
            throw IllegalArgumentException(errorMessage)
        }
    }
}
