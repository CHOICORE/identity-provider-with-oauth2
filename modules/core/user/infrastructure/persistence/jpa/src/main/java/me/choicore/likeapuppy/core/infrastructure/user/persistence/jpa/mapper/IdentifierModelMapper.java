package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Identifier;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserIdentifierEntity;

public class IdentifierModelMapper {

    public static Identifier toDomain(UserIdentifierEntity entity) {
        if (entity == null) return null;

        return new Identifier(
                entity.getEmail(),
                entity.getPhoneNumber()
        );
    }
}
