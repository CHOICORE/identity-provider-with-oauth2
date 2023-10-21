package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;

import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u " +
            "FROM UserEntity u " +
            "WHERE u.email = :identifier " +
            "OR u.phoneNumber = :identifier")
    Optional<UserEntity> findByIdentifier(@Param("identifier") String identifier);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    @Modifying(clearAutomatically = true)
    @Query(
            "UPDATE UserEntity u " +
                    "SET u.authentication.failedLoginAttempts = :failedLoginAttempts, " +
                    "u.authentication.lastLoggedInAt = :lastLoggedInAt, " +
                    "u.authentication.passwordExpirationAt = :passwordExpirationAt " +
                    "WHERE u.id = :id"
    )
    void modifyAuthentication(
            @Param("id") long id,
            @Param("failedLoginAttempts") int failedLoginAttempts,
            @Param("lastLoggedInAt") Instant lastLoggedInAt,
            @Param("passwordExpirationAt") Instant passwordExpirationAt
    );
}
