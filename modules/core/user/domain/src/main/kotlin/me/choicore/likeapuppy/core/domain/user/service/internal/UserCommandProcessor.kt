package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository

class UserCommandProcessor(
    private val userRepository: UserCommandRepository,
) {
    fun register(
        command: RegisterUserCommand.WithAuthorityIds,
    ): Long {
        return userRepository.register(command = command)
    }
}
