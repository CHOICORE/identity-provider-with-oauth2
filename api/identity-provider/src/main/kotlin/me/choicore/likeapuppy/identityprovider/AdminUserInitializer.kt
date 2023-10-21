package me.choicore.likeapuppy.identityprovider

import me.choicore.likeapuppy.core.domain.user.command.UserRegisterCommand.ContainsAuthorityIds
import me.choicore.likeapuppy.core.domain.user.model.PersonalName
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AdminUserInitializer(
    private val userCommandRepository: UserCommandRepository,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val command = ContainsAuthorityIds(
            "admin",
            passwordEncoder.encode("admin"),
            PersonalName("길동", "홍"),
            LocalDate.of(1993, 9, 22),
            Gender.MALE,
            "01043214321",
            listOf(1L, 2L, 3L),
            listOf(1L, 2L),
        )
        userCommandRepository.register(command)
    }
}
