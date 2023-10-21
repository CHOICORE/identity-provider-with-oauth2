package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.Username
import me.choicore.likeapuppy.core.domain.user.model.aggregate.Account
import me.choicore.likeapuppy.core.domain.user.repository.AccountQueryRepository

class AccountQueryProcessor(
    private val repository: AccountQueryRepository,
) {
    fun existsAccountByUsername(
        username: Username,
    ): Boolean {
        username.validate()
        return repository.existsAccountByUsername(username)
    }

    fun findUserAccountByUsername(
        username: Username,
    ): Account {
        username.validate()
        return repository.findUserAccountByUsername(username)
    }
}
