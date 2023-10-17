package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u " +
            "FROM UserEntity u " +
            "WHERE u.email = :username " +
            "OR u.phoneNumber = :username")
    Optional<UserEntity> findByIdentifier(@Param("identifier") String identifier);


}
