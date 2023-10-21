package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Profile;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class ProfileMapper {

    public static Profile toDomain(
            final UserEntity entity
    ) {
        validateNotNull(entity, "UserEntity must not be null");

        return new Profile(
                entity.getId(),
                entity.getNickname(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                PersonalNameMapper.toDomain(entity),
                entity.getDateOfBirth(),
                GenderMapper.toDomain(entity.getGender()),
                entity.getAboutMe()
        );
    }
}
