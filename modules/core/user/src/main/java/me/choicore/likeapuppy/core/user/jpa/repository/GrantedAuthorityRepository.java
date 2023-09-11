package me.choicore.likeapuppy.core.user.jpa.repository;

import me.choicore.likeapuppy.core.user.jpa.entity.GrantedAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantedAuthorityRepository extends JpaRepository<GrantedAuthority, Long> {
}
