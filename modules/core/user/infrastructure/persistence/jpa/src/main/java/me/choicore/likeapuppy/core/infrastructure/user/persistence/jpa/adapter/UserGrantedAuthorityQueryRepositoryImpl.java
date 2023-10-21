package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority;
import me.choicore.likeapuppy.core.domain.user.repository.UserGrantedAuthorityQueryRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserGrantedAuthorityJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserGrantedAuthorityQueryRepositoryImpl implements UserGrantedAuthorityQueryRepository {

    private final UserGrantedAuthorityJpaRepository userGrantedAuthorityJpaRepository;

    @NotNull
    @Override
    public List<GrantedAuthority> findUserGrantedAuthoritiesByUsername(@NotNull String username) {
        return List.of();
    }
}
