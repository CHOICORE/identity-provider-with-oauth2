package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.PersonalName;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import org.springframework.util.StringUtils;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class PersonalNameMapper {

    public static PersonalName toDomain(
            final UserEntity entity
    ) {
        validateNotNull(entity, "UserEntity must not be null");

        return toDomain(

                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName()
        );
    }

    public static PersonalName toDomain(
            final String firstName,
            final String middleName,
            final String lastName
    ) {
        if (!StringUtils.hasText(firstName)) throw new IllegalArgumentException("First name must not be blank");
        if (!StringUtils.hasText(lastName)) throw new IllegalArgumentException("Last name must not be blank");
        return new PersonalName(
                firstName,
                middleName,
                lastName
        );
    }
}
