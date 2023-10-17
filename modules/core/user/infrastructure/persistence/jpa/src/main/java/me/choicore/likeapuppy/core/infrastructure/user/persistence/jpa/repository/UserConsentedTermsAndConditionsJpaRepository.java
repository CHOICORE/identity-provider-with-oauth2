package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;


import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentedTermsAndConditionsEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConsentedTermsAndConditionsJpaRepository extends JpaRepository<UserConsentedTermsAndConditionsEntity, Long> {

    @Query("SELECT uc, tac " +
            "FROM UserConsentedTermsAndConditionsEntity uc join fetch TermsAndConditionsEntity tac on uc.termsAndConditions.id = tac.id " +
            "WHERE uc.user.id = :userId")
    List<UserConsentedTermsAndConditionsEntity> findByUserId(@Param(("userId")) UserId userId);
}
