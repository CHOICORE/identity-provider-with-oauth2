package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.choicore.likeapuppy.core.domain.user.command.UserRegisterCommand;
import me.choicore.likeapuppy.core.domain.user.model.Authentication;
import me.choicore.likeapuppy.core.domain.user.model.aggregate.User;
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentedTermsAndConditionsEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserGrantedAuthorityEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.id.UserId;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.AuthorityJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.TermsAndConditionsJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserConsentedTermsAndConditionsJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserGrantedAuthorityJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCommandRepositoryImpl implements UserCommandRepository {

    private final UserJpaRepository userJpaRepository;
    private final AuthorityJpaRepository authorityJpaRepository;
    private final UserGrantedAuthorityJpaRepository userGrantedAuthorityJpaRepository;
    private final UserConsentedTermsAndConditionsJpaRepository userConsentedTermsAndConditionsJpaRepository;
    private final TermsAndConditionsJpaRepository termsAndConditionsJpaRepository;

    @Override
    @Transactional
    public void modifyAuthentication(
            long id,
            @NotNull final Authentication authentication
    ) {
        log.info("modifyAuthentication id: {}, authentication: {}", id, authentication);
        userJpaRepository.modifyAuthentication(
                id,
                authentication.getFailedLoginAttempts(),
                authentication.getLastLoggedInAt(),
                authentication.getPasswordExpirationAt()
        );
    }

    @Override
    @Transactional
    public long register(
            @NotNull UserRegisterCommand.ContainsAuthorityIds command
    ) {
        return 0;
    }

    private void saveUserGrantedAuthorities(List<Long> authorityIds, UserId userId, Instant grantedAt) {
        authorityIds.forEach(
                authorityId -> {
                    var userGrantedAuthority = UserGrantedAuthorityEntity.builder()
                            .userId(userId)
                            .authority(authorityJpaRepository.getReferenceById(authorityId))
                            .grantedAt(grantedAt)
                            .build();
                    userGrantedAuthorityJpaRepository.save(userGrantedAuthority);
                }
        );
    }

    private void saveUserConsentedTermsAndConditions(List<Long> termsAndConditionsIds, UserId userId, Instant consentedAt) {
        termsAndConditionsIds.forEach(
                termsAndConditionsId -> {
                    var userConsent = UserConsentedTermsAndConditionsEntity.builder()
                            .user(userJpaRepository.getReferenceById(userId.getValue()))
                            .termsAndConditions(termsAndConditionsJpaRepository.getReferenceById(termsAndConditionsId))
                            .consented(true)
                            .build();
                    userConsentedTermsAndConditionsJpaRepository.save(userConsent);
                }
        );
    }

    @Override
    @Transactional
    public void modify(@NotNull User user) {
        log.info("UserCommandRepositoryImpl.modify user: {}", user);
        long id = user.getId();
        // TODO: implement this modify method
        UserEntity found = userJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public void deleteById(long id) {
        log.info("UserCommandRepositoryImpl.deleteById id: {}", id);
        // TODO: implement this cascade delete method,
    }

    @Override
    public long register(@NotNull User user) {
        return 0;

    }
}

