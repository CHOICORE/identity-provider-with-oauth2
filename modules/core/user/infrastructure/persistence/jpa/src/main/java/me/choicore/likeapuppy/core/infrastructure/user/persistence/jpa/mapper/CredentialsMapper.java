package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.Credentials;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import org.springframework.util.StringUtils;

public class CredentialsMapper {

    public static Credentials toDomain(
            final UserEntity entity
    ) {
        if (entity == null) throw new IllegalArgumentException("User Entity is invalid must not be null");
        return toDomain(entity.getEmail(), entity.getPassword());
    }

    public static Credentials toDomain(
            final String username,
            final String password
    ) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Username or password is invalid must not be not blank");
        }
        return new Credentials(username, password);
    }
}
