package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, Long> {

}
