package me.choicore.likeapuppy.core.user.jpa.repository;

import me.choicore.likeapuppy.core.SpringBootDataJpaTest;
import me.choicore.likeapuppy.core.user.jpa.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Set;


@ActiveProfiles("test")
@SpringBootDataJpaTest
class UserRepositoryTest {

	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	private final GrantedAuthorityRepository grantedAuthorityRepository;
	private final TestEntityManager testEntityManager;


	UserRepositoryTest(
			final UserRepository userRepository,
			final AuthorityRepository authorityRepository,
			final GrantedAuthorityRepository grantedAuthorityRepository,
			final TestEntityManager testEntityManager
	) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.grantedAuthorityRepository = grantedAuthorityRepository;
		this.testEntityManager = testEntityManager;
	}


	@Test
	void t1() {

		// given
		final String email = "choijaehyeong93@gmail.com";
		final String mobile = "01012341234";
		final String password = "1q2w3e4r!";
		final String firstName = "Jae-hyeong";
		final String lastName = "Choi";
		final Gender gender = Gender.M;
		final String nickname = "최코어";
		final String profileImageUri = "";
		final Set<UserAuthorityType> userAuthorityTypes = Set.of(UserAuthorityType.USER, UserAuthorityType.ADMIN);

		// when
		Username username = new Username(firstName, lastName);
		Identifier identifier = new Identifier(email, mobile);
		Credential credential = new Credential(identifier, password);
		AccountStatus accountStatus = new AccountStatus(0, null, Instant.now(), false, null, false, null);
		User registerUser = User.builder()
				.credential(credential)
				.username(username)
				.nickname(nickname)
				.picture(profileImageUri)
				.gender(gender)
				.accountStatus(accountStatus)
				.build();

		userRepository.save(registerUser);

		authorityRepository.findByScope("ACCOUNT")
				.stream()
				.filter(authority -> userAuthorityTypes.contains(UserAuthorityType.valueOf(authority.getName())))
				.map(authority -> GrantedAuthority.builder()
						.userId(registerUser.getId())
						.authority(authority)
						.grantedAt(Instant.now())
						.build())
				.forEach(grantedAuthorityRepository::save);
		testEntityManager.flush();
		testEntityManager.clear();
	}
}
