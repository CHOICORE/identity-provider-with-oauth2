package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.likeapuppy.core.domain.user.model.Authentication;
import me.choicore.likeapuppy.core.domain.user.model.Credentials;
import me.choicore.likeapuppy.core.domain.user.model.aggregate.User;
import me.choicore.likeapuppy.core.domain.user.repository.UserQueryRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.AuthenticationMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.CredentialsMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.aggregate.UserMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final UserJpaRepository userJpaRepository;

    @NotNull
    @Override
    public User findUserById(long id) {
        return userJpaRepository.findById(id)
                .map(UserMapper::toDomain)
                .orElseThrow(() -> new IllegalStateException("사용자 정보를 찾을 수 없습니다."));
    }

    @NotNull
    @Override
    public Credentials findUserCredentialsByUsername(@NotNull String username) {
        return userJpaRepository.findByIdentifier(username)
                .map(entity -> CredentialsMapper.toDomain(entity.getEmail(), entity.getPassword()))
                .orElseThrow(() -> new IllegalStateException("사용자 정보를 찾을 수 없습니다."));
    }

    @Override
    public boolean existsByUsername(@NotNull String username) {
        return userJpaRepository.existsByEmailOrPhoneNumber(username, username);
    }

    @NotNull
    @Override
    public Authentication findUserAuthenticationByUsername(@NotNull String username) {
        return userJpaRepository.findByIdentifier(username)
                .map(AuthenticationMapper::toDomain)
                .orElseThrow(() -> new IllegalStateException("사용자 정보를 찾을 수 없습니다."));
    }
}


