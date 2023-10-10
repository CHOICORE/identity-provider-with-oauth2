package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand

interface UserCommandRepository {

    fun register(command: RegisterUserCommand.ContainsAuthorityIds): Long
}
