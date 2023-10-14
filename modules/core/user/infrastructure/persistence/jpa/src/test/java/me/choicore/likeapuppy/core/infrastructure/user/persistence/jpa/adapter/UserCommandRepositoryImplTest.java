package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import me.choicore.likeapuppy.core.SpringBootDataJpaTest;
import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand;
import me.choicore.likeapuppy.core.domain.user.model.DateOfBirth;
import me.choicore.likeapuppy.core.domain.user.model.Username;
import me.choicore.likeapuppy.core.domain.user.model.constant.Gender;
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@ActiveProfiles("test")
@SpringBootDataJpaTest
class UserCommandRepositoryImplTest {

    @Autowired
    private UserCommandRepository userCommandRepository;

    @Test
    @DisplayName("사용자 등록 성공")
    void save() {
        RegisterUserCommand.ContainsAuthorityIds command = new RegisterUserCommand.ContainsAuthorityIds(
                "121253",
                "1",
                new Username("first", "last"),
                new DateOfBirth(1993, 9, 22),
                Gender.MALE,
                "01012341234",
                List.of(1L, 2L, 3L),
                List.of(1L, 2L)
        );
        userCommandRepository.register(command);
    }

    @Test
    @DisplayName("사용자 등록 실패")
    void failsRegistration() {
        // given
        RegisterUserCommand.ContainsAuthorityIds command = new RegisterUserCommand.ContainsAuthorityIds(
                "failed@github.com",
                "1",
                new Username("first", "last"),
                new DateOfBirth(1993, 9, 22),
                Gender.MALE,
                "01012344321",
                List.of(1L, 2L, 3L),
                List.of(1L, 3L)
        );

        // then
        Assertions.assertThatThrownBy(() -> userCommandRepository.register(command));


    }
}
