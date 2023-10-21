package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.aggregate.User
import me.choicore.likeapuppy.core.domain.user.repository.UserQueryRepository

class UserQueryProcessor(
    private val repository: UserQueryRepository,
) {
    fun getUserById(id: Long): User {
        check(id > 0) { "조회에 필요한 사용자 번호는 0보다 커야 합니다." }
        return repository.findUserById(id)
    }

    fun validateUsername(username: String) {
        check(username.isNotBlank()) { "사용자 아이디는 필수입니다." }
        check(repository.existsByUsername(username).not()) { "이미 사용중인 사용자 아이디입니다." }
    }
}
