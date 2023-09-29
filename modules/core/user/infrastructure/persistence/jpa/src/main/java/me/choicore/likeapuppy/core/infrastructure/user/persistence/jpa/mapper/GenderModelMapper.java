package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.constant.Gender;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.GenderEntity;

public class GenderModelMapper {

    public static Gender toDomain(GenderEntity entity) {
        return switch (entity) {
            case M -> Gender.MALE;
            case F -> Gender.FEMALE;
            default -> Gender.UNKNOWN;
        };
    }

    public static GenderEntity toEntity(Gender domain) {
        return switch (domain) {
            case MALE -> GenderEntity.M;
            case FEMALE -> GenderEntity.F;
            default -> GenderEntity.U;
        };
    }
}
