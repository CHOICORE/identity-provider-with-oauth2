package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.User

interface UserQueryRepository {

    fun findByUserIdentifier(identifier: String): User

    fun findById(id: Long): User

    fun existsByUserIdentifier(identifier: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
