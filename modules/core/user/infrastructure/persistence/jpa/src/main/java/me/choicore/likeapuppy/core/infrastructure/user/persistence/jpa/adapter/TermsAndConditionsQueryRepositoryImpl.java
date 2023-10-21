package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.likeapuppy.core.domain.user.model.TermsAndConditions;
import me.choicore.likeapuppy.core.domain.user.repository.TermsAndConditionsQueryRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.TermsAndConditionsEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.TermsAndConditionsMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.TermsAndConditionsJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermsAndConditionsQueryRepositoryImpl implements TermsAndConditionsQueryRepository {

    private final TermsAndConditionsJpaRepository termsAndConditionsJpaRepository;

    @NotNull
    @Override
    public List<TermsAndConditions> find(final boolean usedOnly) {
        List<TermsAndConditionsEntity> entities;

        if (usedOnly) {
            entities = termsAndConditionsJpaRepository.findByUsedIsTrue();
        } else {
            entities = termsAndConditionsJpaRepository.findAll();
        }

        return TermsAndConditionsMapper.toDomains(entities);
    }

    @NotNull
    @Override
    public List<TermsAndConditions> findByMandatoryOnly() {
        List<TermsAndConditionsEntity> entities = termsAndConditionsJpaRepository.findByMandatoryIsTrue();
        return TermsAndConditionsMapper.toDomains(entities);
    }

    @NotNull
    @Override
    public List<TermsAndConditions> findByIds(@NotNull final List<Long> ids) {
        List<TermsAndConditionsEntity> entities = termsAndConditionsJpaRepository.findByIdIn(ids);
        return TermsAndConditionsMapper.toDomains(entities);
    }
}
