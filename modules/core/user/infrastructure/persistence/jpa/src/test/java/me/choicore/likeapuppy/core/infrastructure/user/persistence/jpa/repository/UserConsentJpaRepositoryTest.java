package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.SpringBootDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootDataJpaTest
class UserConsentJpaRepositoryTest {

    @Autowired
    private UserConsentJpaRepository userConsentJpaRepository;

    @Test
    void t1() {
    }
}
