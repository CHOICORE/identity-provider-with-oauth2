package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand;
import me.choicore.likeapuppy.core.domain.user.model.User;
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.AuthenticationEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.CredentialsEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserGrantedAuthorityEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserIdentifierEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserProfileEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.VerificationEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.id.UserId;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.mapper.GenderModelMapper;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.AuthorityJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.TermsAndConditionsJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserConsentJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserGrantedAuthorityJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserJpaRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.repository.UserProfileJpaRepository;
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
    private final UserConsentJpaRepository userConsentJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final TermsAndConditionsJpaRepository termsAndConditionsJpaRepository;

    @Override
    @Transactional
    public long register(
            @NotNull RegisterUserCommand.ContainsAuthorityIds command
    ) {
        log.info("UserCommandRepositoryImpl.register command: {}", command);

        Instant now = Instant.now();
        AuthenticationEntity authentication = AuthenticationEntity.builder()
                .identifier(new UserIdentifierEntity(command.getEmail(), command.getPhoneNumber()))
                .credentials(new CredentialsEntity(command.getPassword()))
                .lastLoggedInAt(null)
                .build();

        UserEntity user = UserEntity.builder()
                .authentication(authentication)
                .verification(VerificationEntity.Unverified())
                .build();

        userJpaRepository.save(user);

        UserId userId = UserId.of(user.getId());

        UserProfileEntity profile = UserProfileEntity.builder()
                .userId(userId)
                .firstName(command.getUsername().getFirstName())
                .lastName(command.getUsername().getLastName())
                .dateOfBirth(command.getDateOfBirth().toLocalDate())
                .gender(GenderModelMapper.toEntity(command.getGender()))
                .build();

        userProfileJpaRepository.save(profile);

        saveUserGrantedAuthorities(command.getAuthorityIds(), userId, now);
        saveUserConsentedTermsAndConditions(command.getTermsAndConditionsIds(), userId, now);
        return userId.getValue();
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
                    var userConsent = UserConsentEntity.builder()
                            .user(userJpaRepository.getReferenceById(userId.getValue()))
                            .termsAndConditions(termsAndConditionsJpaRepository.getReferenceById(termsAndConditionsId))
                            .consented(true)
                            .build();
                    userConsentJpaRepository.save(userConsent);
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
}

