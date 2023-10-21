package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Verification;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.VerificationEntity;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class VerificationMapper {

    public static Verification toDomain(VerificationEntity entity) {
        validateNotNull(entity, "Verification Entity is invalid must not be null");

        return new Verification(
                entity.isEmailVerified(),
                entity.getEmailVerifiedAt(),
                entity.isPhoneNumberVerified(),
                entity.getPhoneNumberVerifiedAt()
        );
    }
}
