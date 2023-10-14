package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.SpringBootDataJpaTest;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootDataJpaTest
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void test() {

        long userId = 2L;
        UserEntity foundEntity = userJpaRepository.findById(userId).orElseThrow();
        Assertions.assertThat(foundEntity).isNotNull();
        Assertions.assertThat(foundEntity.getId()).isEqualTo(userId);
    }
}
