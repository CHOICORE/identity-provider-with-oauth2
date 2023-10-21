package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.command.UserRegisterCommand
import me.choicore.likeapuppy.core.domain.user.model.aggregate.User
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository

class UserCommandProcessor(
    private val repository: UserCommandRepository,
) {
    fun register(command: UserRegisterCommand.ContainsAuthorityIds): Long {
        try {
            return repository.register(command = command)
        } catch (e: RuntimeException) {
            throw IllegalArgumentException("Failed to register user", e)
        }
    }

    fun register(user: User): Long {
        try {
            return repository.register(user = user)
        } catch (e: RuntimeException) {
            throw IllegalArgumentException("Failed to register user", e)
        }
    }

    fun modify(user: User) {
        repository.modify(user)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}
