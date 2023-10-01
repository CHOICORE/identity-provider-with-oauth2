package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Consent;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConsentModelMapper {

    public static List<Consent> toDomain(List<UserConsentEntity> entities) {
        if (entities.isEmpty()) return Collections.emptyList();

        return entities.stream()
                .map(ConsentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static Consent toDomain(UserConsentEntity entity) {
        if (entity == null) return null;

        return new Consent(
                TermsAndConditionsModelMapper.toDomain(entity.getTermsAndConditions()),
                entity.isAgreed(),
                entity.getAgreedAt()
        );
    }
}

