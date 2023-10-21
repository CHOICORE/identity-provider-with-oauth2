package me.choicore.likeapuppy.core.domain.user.exception

class AccountNotFoundException(
    message: String? = "Account not found",
) : IllegalStateException(message)
