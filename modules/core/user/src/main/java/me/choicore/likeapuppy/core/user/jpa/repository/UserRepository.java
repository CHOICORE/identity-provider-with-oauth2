package me.choicore.likeapuppy.core.user.jpa.repository;

import me.choicore.likeapuppy.core.user.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u " +
			"FROM User u " +
			"WHERE u.credential.identifier.email = :identifier " +
			"OR u.credential.identifier.mobile = :identifier")
	Optional<User> findByIdentifier(@Param("identifier") String identifier);
}
