package me.choicore.likeapuppy.identityprovider

import me.choicore.likeapuppy.core.user.jpa.entity.AccountStatus
import me.choicore.likeapuppy.core.user.jpa.entity.Authority
import me.choicore.likeapuppy.core.user.jpa.entity.Credential
import me.choicore.likeapuppy.core.user.jpa.entity.Gender
import me.choicore.likeapuppy.core.user.jpa.entity.GrantedAuthority
import me.choicore.likeapuppy.core.user.jpa.entity.Identifier
import me.choicore.likeapuppy.core.user.jpa.entity.User
import me.choicore.likeapuppy.core.user.jpa.entity.UserAuthorityType
import me.choicore.likeapuppy.core.user.jpa.entity.Username
import me.choicore.likeapuppy.core.user.jpa.repository.AuthorityRepository
import me.choicore.likeapuppy.core.user.jpa.repository.GrantedAuthorityRepository
import me.choicore.likeapuppy.core.user.jpa.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Component
class AdminUserInitializer(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val grantedAuthorityRepository: GrantedAuthorityRepository,
    private val authorityRepository: AuthorityRepository,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments) {
        val password: String = passwordEncoder.encode("1")

        val userAuthorityTypes: MutableSet<UserAuthorityType> =
            mutableSetOf(UserAuthorityType.USER, UserAuthorityType.ADMIN)

        val identifier: Identifier = Identifier("1", "01012341234")

        val accountStatus: AccountStatus = AccountStatus.withVerifiedCredentials().build()

        val registeredAt: Instant = Instant.now()

        val registerUser: User = User.builder()
            .credential(Credential(identifier, password))
            .username(Username("Jae-hyeong", "Choi"))
            .nickname("CHOICORE")
            .picture("")
            .gender(Gender.M)
            .accountStatus(accountStatus)
            .registeredAt(registeredAt)
            .build()

        userRepository.save(registerUser)

        authorityRepository.findByScope("ACCOUNT")
            .filter { authority: Authority -> userAuthorityTypes.contains(UserAuthorityType.valueOf(authority.name)) }
            .forEach {
                val grantedAuthority: GrantedAuthority = GrantedAuthority.builder()
                    .authority(it)
                    .userId(registerUser.id)
                    .grantedAt(registeredAt)
                    .build()
                grantedAuthorityRepository.save(grantedAuthority)
            }
    }
}
