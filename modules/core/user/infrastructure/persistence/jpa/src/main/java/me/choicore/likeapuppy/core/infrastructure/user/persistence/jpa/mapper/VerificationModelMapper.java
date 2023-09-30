package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Verification;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.VerificationEntity;

public class VerificationModelMapper {

    public static Verification toDomain(VerificationEntity entity) {
        if (entity == null) return null;

        return new Verification(
                entity.isEmailVerified(),
                entity.getEmailVerifiedAt(),
                entity.isPhoneNumberVerified(),
                entity.getPhoneNumberVerifiedAt()
        );
    }
}
