package me.choicore.likeapuppy.identityprovider.common

import java.util.*

internal fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)

internal fun <T : Any> T?.toOptional(): Optional<T> = Optional.ofNullable(this)
