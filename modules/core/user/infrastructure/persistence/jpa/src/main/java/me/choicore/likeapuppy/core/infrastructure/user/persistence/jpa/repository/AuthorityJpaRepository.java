package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;


import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(String name);

    List<Authority> findByScope(String scope);
}
