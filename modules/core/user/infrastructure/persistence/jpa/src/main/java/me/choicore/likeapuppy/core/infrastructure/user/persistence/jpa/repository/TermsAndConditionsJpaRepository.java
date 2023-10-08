package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository;


import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.TermsAndConditionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsAndConditionsJpaRepository extends JpaRepository<TermsAndConditionsEntity, Long> {

    List<TermsAndConditionsEntity> findByUsedIsTrue();

    List<TermsAndConditionsEntity> findByMandatoryIsTrue();

    List<TermsAndConditionsEntity> findByIdIn(List<Long> ids);
}
