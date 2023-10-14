package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand
import me.choicore.likeapuppy.core.domain.user.model.User

interface UserCommandRepository {

    fun register(command: RegisterUserCommand.ContainsAuthorityIds): Long

    fun modify(user: User)

    fun deleteById(id: Long)
}
