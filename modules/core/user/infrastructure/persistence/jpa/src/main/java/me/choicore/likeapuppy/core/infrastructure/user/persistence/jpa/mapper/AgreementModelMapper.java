package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Agreement;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserAgreementEntity;

public class AgreementModelMapper {

    public static Agreement toDomain(UserAgreementEntity entity) {
        if (entity == null) return null;

        return new Agreement(
                entity.getId(),
                entity.getSubject(),
                entity.getContent(),
                entity.getDescription(),
                entity.isRequired(),
                entity.getRegisteredAt(),
                entity.getModifiedAt()
        );
    }
}
