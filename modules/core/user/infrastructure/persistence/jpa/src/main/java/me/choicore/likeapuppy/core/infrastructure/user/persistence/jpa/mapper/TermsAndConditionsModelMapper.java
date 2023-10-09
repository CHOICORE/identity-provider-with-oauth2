package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.TermsAndConditions;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.TermsAndConditionsEntity;

import java.util.Collections;
import java.util.List;

public class TermsAndConditionsModelMapper {

    public static TermsAndConditions toDomain(TermsAndConditionsEntity entity) {
        if (entity == null) return null;

        return new TermsAndConditions(
                entity.getId(),
                entity.getSubject(),
                entity.getContent(),
                entity.getDescription(),
                entity.isMandatory(),
                entity.isUsed(),
                entity.getVersion(),
                entity.getRegisteredAt(),
                entity.getModifiedAt()
        );
    }

    public static List<TermsAndConditions> toDomains(List<TermsAndConditionsEntity> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(TermsAndConditionsModelMapper::toDomain)
                .toList();
    }
}
