package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.likeapuppy.core.domain.user.exception.AccountNotFoundException;
import me.choicore.likeapuppy.core.domain.user.model.Username;
import me.choicore.likeapuppy.core.domain.user.model.aggregate.Account;
import me.choicore.likeapuppy.core.domain.user.repository.AccountQueryRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.AccountMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsAccountByUsername(@NotNull Username username) {
        return switch (username.getTypeHints()) {
            case EMAIL -> userJpaRepository.existsByEmail(username.getUsername());
            case PHONE_NUMBER -> userJpaRepository.existsByPhoneNumber(username.getUsername());
        };
    }

    @NotNull
    @Override
    public Account findUserAccountByUsername(
            @NotNull final Username username
    ) {
        return switch (username.getTypeHints()) {
            case EMAIL -> findUserAccountByEmail(username.getUsername());
            case PHONE_NUMBER -> findUserAccountByPhoneNumber(username.getUsername());
        };
    }

    public Account findUserAccountByPhoneNumber(
            @NotNull final String phoneNumber
    ) {
        if (phoneNumber.isBlank()) {
            throw new IllegalArgumentException("전화번호는 필수값입니다.");
        }

        UserEntity entity = userJpaRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AccountNotFoundException("해당하는 계정이 없습니다."));
        return AccountMapper.toDomain(entity);
    }

    public Account findUserAccountByEmail(
            @NotNull final String email
    ) {
        if (email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수값입니다.");
        }

        UserEntity entity = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("해당하는 계정이 없습니다."));
        return AccountMapper.toDomain(entity);
    }
}
