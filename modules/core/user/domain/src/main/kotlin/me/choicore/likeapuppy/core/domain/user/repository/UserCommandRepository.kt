package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.command.UserRegisterCommand
import me.choicore.likeapuppy.core.domain.user.model.Authentication
import me.choicore.likeapuppy.core.domain.user.model.aggregate.User

interface UserCommandRepository {
    fun register(command: UserRegisterCommand.ContainsAuthorityIds): Long

    fun register(user: User): Long

    fun modify(user: User)

    fun deleteById(id: Long)

    fun modifyAuthentication(
        id: Long,
        authentication: Authentication,
    )
}
