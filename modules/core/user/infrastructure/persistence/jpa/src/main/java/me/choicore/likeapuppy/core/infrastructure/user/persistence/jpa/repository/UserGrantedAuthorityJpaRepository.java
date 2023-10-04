package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserGrantedAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGrantedAuthorityJpaRepository extends JpaRepository<UserGrantedAuthorityEntity, Long> {
}
