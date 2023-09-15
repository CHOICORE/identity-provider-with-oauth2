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
        val email = "1"
        val mobile = "01012341234"
        val password = passwordEncoder.encode("1")
        val firstName = "Jae-hyeong"
        val lastName = "Choi"
        val gender = Gender.M
        val nickname = "최코어"
        val picture = ""
        val userAuthorityTypes: MutableSet<UserAuthorityType> =
            mutableSetOf(UserAuthorityType.USER, UserAuthorityType.ADMIN)

        val username = Username(firstName, lastName)
        val identifier = Identifier(email, mobile)
        val credential = Credential(identifier, password)
        val accountStatus = AccountStatus(0, null, Instant.now(), false, null, false, null)
        val registerUser = User.builder()
            .credential(credential)
            .username(username)
            .nickname(nickname)
            .picture(picture)
            .gender(gender)
            .accountStatus(accountStatus)
            .registeredAt(Instant.now())
            .build()

        userRepository.save(registerUser)

        authorityRepository.findByScope("ACCOUNT")
            .filter { authority: Authority -> userAuthorityTypes.contains(UserAuthorityType.valueOf(authority.name)) }
            .map {
                GrantedAuthority.builder()
                    .authority(it)
                    .userId(registerUser.id)
                    .grantedAt(Instant.now())
                    .build()
            }
            .forEach { grantedAuthorityRepository.save(it) }
    }
}
