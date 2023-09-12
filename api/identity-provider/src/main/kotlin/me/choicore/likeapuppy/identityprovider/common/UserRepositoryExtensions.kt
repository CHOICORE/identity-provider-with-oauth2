package me.choicore.likeapuppy.identityprovider.common

import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository

internal fun UserRepository.findByIdentifierOrNull(identifier: String):
		me.choicore.likeapuppy.core.user.jpa.entity.User? {
	return findByIdentifier(identifier).toNullable()
}