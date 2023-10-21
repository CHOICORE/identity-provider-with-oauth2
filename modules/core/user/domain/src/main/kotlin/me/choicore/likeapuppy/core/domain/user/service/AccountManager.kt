package me.choicore.likeapuppy.core.domain.user.service

import me.choicore.likeapuppy.core.common.Encryptor
import me.choicore.likeapuppy.core.domain.user.model.Username
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountCommandProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.AccountQueryProcessor

class AccountManager(
    private val accountCommandProcessor: AccountCommandProcessor,
    private val accountQueryProcessor: AccountQueryProcessor,
    private val encryptor: Encryptor,
) {

    /**
     * 회원 가입
     * 1. 해당하는 username이 존재하는지 확인한다.
     * 2. 존재하지 않는다면, 회원 가입을 진행한다.
     * 3. 필수 약관 동의 여부를 확인한다.
     * 4. 필수 약관 동의를 하지 않았다면, 회원 가입을 진행하지 않는다.
     */
    fun createAccount(
        username: Username,
        password: String,
    ) {
        check(existsAccountByUsername(username)) {
            "이미 존재하는 계정이 있습니다."
        }

        val encryptPassword: String = encryptor.encode(password)
    }

    private fun existsAccountByUsername(username: Username): Boolean {
        return accountQueryProcessor.existsAccountByUsername(username)
    }
}
