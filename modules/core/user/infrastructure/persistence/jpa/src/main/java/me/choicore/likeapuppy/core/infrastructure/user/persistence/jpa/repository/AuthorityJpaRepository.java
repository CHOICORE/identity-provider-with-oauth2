package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;


import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<AuthorityEntity, Long> {

    Optional<AuthorityEntity> findByName(String name);

    List<AuthorityEntity> findByScope(String scope);
}
