package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.Authentication

interface AccountCommandRepository {
    fun markAsLoggedInStatus(
        userId: Long,
        authentication: Authentication,
    )
}
