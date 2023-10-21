package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.Authentication
import me.choicore.likeapuppy.core.domain.user.model.Credentials
import me.choicore.likeapuppy.core.domain.user.model.aggregate.User

interface UserQueryRepository {
    fun findUserById(id: Long): User

    fun findUserCredentialsByUsername(username: String): Credentials

    fun findUserAuthenticationByUsername(username: String): Authentication

    fun existsByUsername(username: String): Boolean
}
