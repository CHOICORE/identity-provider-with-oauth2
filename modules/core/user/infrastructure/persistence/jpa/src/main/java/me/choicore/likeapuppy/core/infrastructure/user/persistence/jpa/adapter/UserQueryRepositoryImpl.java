package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.likeapuppy.core.domain.user.model.User;
import me.choicore.likeapuppy.core.domain.user.repository.UserQueryRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.UserModelMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final UserJpaRepository userJpaRepository;

    @NotNull
    @Override
    public User findByUserIdentifier(@NotNull String identifier) {
        validateHasText(identifier);

        var entity = userJpaRepository.findByIdentifier(identifier)
                .orElseThrow(throwsIllegalStateException());
        return UserModelMapper.toDomain(entity);
    }

    @NotNull
    @Override
    public User findById(long id) {
        if (id <= 0) throw new IllegalArgumentException("id must be positive");

        var entity = userJpaRepository.findById(id)
                .orElseThrow(throwsIllegalStateException());
        return UserModelMapper.toDomain(entity);
    }

    private Supplier<IllegalStateException> throwsIllegalStateException() {
        return () -> new IllegalStateException("User not found");
    }

    @Override
    public boolean existsByUserIdentifier(@NotNull String identifier) {
        validateHasText(identifier);

        return userJpaRepository.findByIdentifier(identifier).isPresent();
    }

    @Override
    public boolean existsByEmail(@NotNull String email) {
        return userJpaRepository.existsByEmail(email);

    }

    @Override
    public boolean existsByPhoneNumber(@NotNull String phoneNumber) {
        return userJpaRepository.existsByPhoneNumber(phoneNumber);
    }

    private void validateHasText(@NotNull String identifier) {
        if (StringUtils.hasText(identifier)) throw new IllegalArgumentException("identifier must not be empty");
    }
}


