package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper;

import me.choicore.likeapuppy.core.domain.user.model.GrantedAuthority;
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserGrantedAuthorityEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static me.choicore.likeapuppy.core.common.ValidatorKt.validateNotNull;

public class GrantedAuthorityMapper {

    private GrantedAuthorityMapper() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static List<GrantedAuthority> toDomains(UserEntity entity) {
        validateNotNull(entity, "UserEntity must not be null");
        List<UserGrantedAuthorityEntity> grantedAuthorities = entity.getGrantedAuthorities();
        return toDomains(grantedAuthorities);
    }

    public static List<GrantedAuthority> toDomains(List<UserGrantedAuthorityEntity> entities) {
        if (entities.isEmpty()) return Collections.emptyList();

        return entities.stream()
                .map(GrantedAuthorityMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static GrantedAuthority toDomain(UserGrantedAuthorityEntity entity) {
        if (entity == null) return null;

        return new GrantedAuthority(
                AuthorityNames.valueOf(entity.getAuthority().getName()),
                entity.getAuthority().getScope(),
                entity.getGrantedAt()
        );
    }
}
