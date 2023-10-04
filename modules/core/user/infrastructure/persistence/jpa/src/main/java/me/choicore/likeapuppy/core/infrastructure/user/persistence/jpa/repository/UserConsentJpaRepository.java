package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;


import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConsentJpaRepository extends JpaRepository<UserConsentEntity, Long> {


    @Query("SELECT uc, tac " +
            "FROM UserConsentEntity uc join fetch TermsAndConditionsEntity tac on uc.termsAndConditions.id = tac.id " +
            "WHERE uc.userId = :userId")
    List<UserConsentEntity> findByUserId(@Param(("userId")) UserId userId);

}
