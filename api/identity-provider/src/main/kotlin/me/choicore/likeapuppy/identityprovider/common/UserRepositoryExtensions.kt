package me.choicore.likeapuppy.identityprovider.common

import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository
import me.choicore.likeapuppy.core.user.jpa.entity.User as UserEntity

internal fun UserRepository.findByIdentifierOrNull(identifier: String): UserEntity? {
    return findByIdentifier(identifier).toNullable()
}
