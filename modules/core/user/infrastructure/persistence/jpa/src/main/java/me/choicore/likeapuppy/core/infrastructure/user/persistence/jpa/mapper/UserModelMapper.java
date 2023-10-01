package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.User;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;

public class UserModelMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return new User(
                entity.getId(),
                ProfileModelMapper.toDomain(entity.getProfile()),
                AuthenticationModelMapper.toDomain(entity.getAuthentication()),
                GrantedAuthorityModelMapper.toDomain(entity.getGrantedAuthorities()),
                ConsentModelMapper.toDomain(entity.getAgreements()),
                VerificationModelMapper.toDomain(entity.getVerification())
        );
    }
}
