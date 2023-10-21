package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.aggregate;

import me.choicore.likeapuppy.core.domain.user.model.aggregate.User;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.AuthenticationMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.ConsentedTermsAndConditionsMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.CredentialsMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.GrantedAuthorityMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.ProfileMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.VerificationMapper;

import java.util.Collections;
import java.util.List;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class UserMapper {

    private UserMapper() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static User toDomain(UserEntity entity) {
        validateNotNull(entity, "UserEntity must not be null");
        return new User(entity.getId(),
                CredentialsMapper.toDomain(entity),
                AuthenticationMapper.toDomain(entity),
                ProfileMapper.toDomain(entity),
                GrantedAuthorityMapper.toDomains(entity),
                ConsentedTermsAndConditionsMapper.toDomains(entity.getConsentedTermsAndConditions()),
                VerificationMapper.toDomain(entity.getVerification()),
                entity.getRegisteredAt());
    }

    public static List<User> toDomains(List<UserEntity> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(UserMapper::toDomain)
                .toList();
    }
}
