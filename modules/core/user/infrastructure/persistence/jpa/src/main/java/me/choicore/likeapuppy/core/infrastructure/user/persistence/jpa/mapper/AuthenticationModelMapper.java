package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Authentication;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.AuthenticationEntity;

public class AuthenticationModelMapper {

    public static Authentication toDomain(AuthenticationEntity entity) {
        if (entity == null) return null;

        return new Authentication(
                IdentifierModelMapper.toDomain(entity.getIdentifier()),
                CredentialsModelMapper.toDomain(entity.getCredentials()),
                entity.getLastLoggedInAt(),
                entity.getRegisteredAt()
        );
    }
}
