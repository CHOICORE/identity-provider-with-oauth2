package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.Authority
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames

interface AuthorityQueryRepository {
    fun findByName(name: String): Authority

    fun findByNames(name: List<AuthorityNames>): List<Authority>

    fun findById(id: Long): Authority

    fun findAll(): List<Authority>

    fun findByIds(ids: List<Long>): List<Authority>

    fun existsByName(name: String): Boolean
}
