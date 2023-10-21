package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.Authentication
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository

class AccountCommandProcessor(
    private val repository: UserCommandRepository,
) {

    fun modifyAccountAuthentication(
        id: Long,
        authentication: Authentication,
    ) {
        repository.modifyAuthentication(id, authentication)
    }
}
