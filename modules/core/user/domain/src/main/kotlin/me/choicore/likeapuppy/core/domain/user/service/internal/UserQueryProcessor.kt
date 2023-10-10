package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.User
import me.choicore.likeapuppy.core.domain.user.repository.UserQueryRepository

class UserQueryProcessor(
    private val userQueryRepository: UserQueryRepository,
) {

    fun getUserById(id: Long): User {
        return userQueryRepository.findById(id)
    }

    fun getUserByUserIdentifier(userIdentifier: String): User {
        return userQueryRepository.findByUserIdentifier(userIdentifier)
    }

    fun existsByPhoneNumber(phoneNumber: String): Boolean {
        return userQueryRepository.existsByPhoneNumber(phoneNumber)
    }

    fun existsByEmail(email: String): Boolean {
        return userQueryRepository.existsByEmail(email)
    }

    fun validateUserIdentifier(email: String, phoneNumber: String?) {
        if (existsByEmail(email)) {
            throw IllegalArgumentException("Email already exists: $email")
        }

        if (phoneNumber != null && existsByPhoneNumber(phoneNumber)) {
            throw IllegalArgumentException("Phone number already exists: $phoneNumber")
        }
    }
}
