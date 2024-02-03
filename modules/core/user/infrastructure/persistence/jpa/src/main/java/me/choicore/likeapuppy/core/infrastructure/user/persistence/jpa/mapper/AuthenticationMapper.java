package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Authentication;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.AuthenticationEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;

import java.time.Instant;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class AuthenticationMapper {

    public static Authentication toDomain(final UserEntity entity) {
        validateNotNull(entity, "User Entity is invalid must not be null");
        return toDomain(entity.getAuthentication());
    }

    public static Authentication toDomain(final AuthenticationEntity entity) {
        validateNotNull(entity, "Authentication Entity is invalid must not be null");
        return toDomain(
                entity.getFailedLoginAttempts(),
                entity.getPasswordExpirationAt(),
                entity.getLastLoggedInAt()
        );
    }

    public static Authentication toDomain(
            final int failedLoginAttempts,
            final Instant passwordExpirationAt,
            final Instant lastLoggedInAt
    ) {
        return new Authentication(
                failedLoginAttempts,
                passwordExpirationAt,
                lastLoggedInAt
        );
    }
}
