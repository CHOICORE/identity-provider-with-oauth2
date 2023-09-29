package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.DateOfBirth;
import me.choicore.likeapuppy.core.domain.user.model.Profile;
import me.choicore.likeapuppy.core.domain.user.model.Username;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserProfileEntity;

public class ProfileModelMapper {
    public static Profile toDomain(UserProfileEntity entity) {
        if (entity == null) return null;

        return new Profile(
                entity.getNickname(),
                new Username(entity.getFirstName(), entity.getLastName()),
                DateOfBirth.of(entity.getDateOfBirth()),
                GenderModelMapper.toDomain(entity.getGender()),
                entity.getAboutMe()
        );
    }
}
