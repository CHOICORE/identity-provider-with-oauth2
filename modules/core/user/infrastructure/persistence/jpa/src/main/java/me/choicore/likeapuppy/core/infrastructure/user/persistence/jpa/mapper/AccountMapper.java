package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Authentication;
import me.choicore.likeapuppy.core.domain.user.model.Credentials;
import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority;
import me.choicore.likeapuppy.core.domain.user.model.aggregate.Account;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;

import java.time.Instant;
import java.util.List;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class AccountMapper {

    private AccountMapper() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static Account toDomain(final UserEntity entity) {
        validateNotNull(entity, "Account Entity is invalid must not be null");

        return toDomain(
                entity.getId(),
                CredentialsMapper.toDomain(entity.getEmail(), entity.getPassword()),
                AuthenticationMapper.toDomain(entity.getAuthentication()),
                GrantedAuthorityMapper.toDomains(entity.getGrantedAuthorities()),
                entity.getRegisteredAt()
        );
    }

    public static Account toDomain(
            final Long id,
            final Credentials credentials,
            final Authentication authentication,
            final List<GrantedAuthority> authorities,
            final Instant registeredAt
    ) {
        return new Account(
                id,
                credentials,
                authentication,
                authorities,
                registeredAt
        );
    }
}

