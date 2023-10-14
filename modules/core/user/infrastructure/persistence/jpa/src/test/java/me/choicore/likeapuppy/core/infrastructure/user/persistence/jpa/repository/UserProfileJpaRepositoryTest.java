package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.SpringBootDataJpaTest;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserProfileEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootDataJpaTest
class UserProfileJpaRepositoryTest {

    @Autowired
    private UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void test() {
        long userId = 1L;
        Optional<UserProfileEntity> foundEntity = userProfileJpaRepository.findById(userId);
        Assertions.assertThat(foundEntity).isPresent();
        Assertions.assertThat(foundEntity.get().getId()).isEqualTo(userId);
    }
}
