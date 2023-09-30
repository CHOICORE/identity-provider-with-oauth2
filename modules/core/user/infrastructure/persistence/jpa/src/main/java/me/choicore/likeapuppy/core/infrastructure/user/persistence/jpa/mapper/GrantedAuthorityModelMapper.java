package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority;
import me.choicore.likeapuppy.core.domain.user.model.constant.Authority;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserGrantedAuthorityEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GrantedAuthorityModelMapper {

    public static List<GrantedAuthority> toDomain(List<UserGrantedAuthorityEntity> entities) {
        if (entities.isEmpty()) return Collections.emptyList();

        return entities.stream()
                .map(GrantedAuthorityModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static GrantedAuthority toDomain(UserGrantedAuthorityEntity entity) {
        if (entity == null) return null;

        return new GrantedAuthority(
                Authority.valueOf(entity.getAuthorityEntity().getName()),
                entity.getAuthorityEntity().getScope(),
                entity.getGrantedAt()
        );
    }

}
