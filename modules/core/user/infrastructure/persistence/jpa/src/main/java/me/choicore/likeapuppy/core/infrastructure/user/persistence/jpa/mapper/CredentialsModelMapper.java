package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Credentials;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.CredentialsEntity;

public class CredentialsModelMapper {

    public static Credentials toDomain(CredentialsEntity entity) {
        if (entity == null) return null;

        return new Credentials(
                entity.getPassword(),
                entity.getFailedLoginAttempts(),
                entity.getPasswordExpirationAt()
        );
    }
}
