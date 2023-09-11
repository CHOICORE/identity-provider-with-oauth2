package me.choicore.likeapuppy.core.user.jpa.repository;


import me.choicore.likeapuppy.core.user.jpa.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByName(String name);

	List<Authority> findByScope(String scope);
}
