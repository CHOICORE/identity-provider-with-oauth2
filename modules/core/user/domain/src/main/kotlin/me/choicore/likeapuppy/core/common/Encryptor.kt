package me.choicore.likeapuppy.core.common

interface Encryptor {
    fun encode(rawPassword: String): String
}
