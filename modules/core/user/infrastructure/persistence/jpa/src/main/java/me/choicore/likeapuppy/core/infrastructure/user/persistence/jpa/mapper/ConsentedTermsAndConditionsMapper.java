package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.ConsentedTermsAndConditions;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentedTermsAndConditionsEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;

import java.util.Collections;
import java.util.List;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class ConsentedTermsAndConditionsMapper {

    public static List<ConsentedTermsAndConditions> toDomains(UserEntity entity) {
        validateNotNull(entity, "UserEntity must not be null");
        List<UserConsentedTermsAndConditionsEntity> consentedTermsAndConditions = entity.getConsentedTermsAndConditions();
        return toDomains(consentedTermsAndConditions);
    }

    public static List<ConsentedTermsAndConditions> toDomains(List<UserConsentedTermsAndConditionsEntity> entities) {
        if (entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(ConsentedTermsAndConditionsMapper::toDomain)
                .toList();
    }

    public static ConsentedTermsAndConditions toDomain(UserConsentedTermsAndConditionsEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ConsentedTermsAndConditions(
                // TermsAndConditionsModelMapper.toDomain(entity.getTermsAndConditionsId()),
                null,
                entity.isConsented(),
                entity.getConsentedAt()
        );
    }
}

