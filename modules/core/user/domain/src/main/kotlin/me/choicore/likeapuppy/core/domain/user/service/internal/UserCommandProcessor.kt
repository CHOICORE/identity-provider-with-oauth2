package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand
import me.choicore.likeapuppy.core.domain.user.model.User
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository

class UserCommandProcessor(
    private val userRepository: UserCommandRepository,
) {
    fun register(
        command: RegisterUserCommand.ContainsAuthorityIds,
    ): Long {
        try {
            return userRepository.register(command = command)
        } catch (e: RuntimeException) {
            throw IllegalArgumentException("Failed to register user", e)
        }
    }

    fun modify(user: User) {
        userRepository.modify(user)
    }

    fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
