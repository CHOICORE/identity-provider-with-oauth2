package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.Username
import me.choicore.likeapuppy.core.domain.user.model.aggregate.Account

interface AccountQueryRepository {

    fun findUserAccountByUsername(
        username: Username,
    ): Account

    fun existsAccountByUsername(username: Username): Boolean
}
