package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.SpringBootDataJpaTest;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.TermsAndConditionsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootDataJpaTest
class TermsAndConditionsJpaRepositoryTest {

    private final TermsAndConditionsJpaRepository termsAndConditionsJpaRepository;


    TermsAndConditionsJpaRepositoryTest(TermsAndConditionsJpaRepository termsAndConditionsJpaRepository) {
        this.termsAndConditionsJpaRepository = termsAndConditionsJpaRepository;
    }

    @Test
    @DisplayName("필수 동의 약관 목록 조회")
    void findByRequiredIsTrue() {

        // when
        List<TermsAndConditionsEntity> termsAndConditions = termsAndConditionsJpaRepository.findByMandatoryIsTrue();

        // then
        assertThat(termsAndConditions).isNotNull();

        termsAndConditions.forEach(termsAndCondition -> {
            assertThat(termsAndCondition.isMandatory()).isTrue();
        });
    }


    @Test
    @DisplayName("ID로 약관 목록 조회")
    void findByIdIn() {

        // given
        List<Long> ids = List.of(1L, 2L);

        // when
        List<TermsAndConditionsEntity> termsAndConditions = termsAndConditionsJpaRepository.findByIdIn(ids);

        // then
        assertThat(termsAndConditions)
                .isNotNull()
                .hasSize(2);
    }


    @Test
    @DisplayName("필수 동의 약관 목록 조회")
    void test() {

        // given
        List<Long> ids = List.of(1L, 2L, 3L, 4L);

        // when
        List<TermsAndConditionsEntity> termsAndConditions = termsAndConditionsJpaRepository.findByIdIn(ids);

        // then
        assertThat(termsAndConditions)
                .isNotNull()
                .hasSize(3);
    }
}
