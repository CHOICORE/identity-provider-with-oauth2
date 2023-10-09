package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand;
import me.choicore.likeapuppy.core.domain.user.repository.UserCommandRepository;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.AuthenticationEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.CredentialsEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserConsentEntity;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.UserEntity;
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
import java.util.Objects;

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
            @NotNull RegisterUserCommand.WithAuthorityIds command
    ) {
        Objects.requireNonNull(command, "command must not be null");

        Instant now = Instant.now();

        AuthenticationEntity authentication = AuthenticationEntity.builder()
                .identifier(new UserIdentifierEntity(command.getEmail(), command.getPhoneNumber()))
                .credentials(new CredentialsEntity(command.getPassword()))
                .lastLoggedInAt(null)
                .registeredAt(now)
                .build();

        UserEntity user = UserEntity.builder()
                .authentication(authentication)
                .verification(VerificationEntity.Unverified())
                .build();

        userJpaRepository.save(user);

        UserProfileEntity profile = UserProfileEntity.builder()
                .userId(UserId.of(user.getId()))
                .firstName(command.getUsername().getFirstName())
                .lastName(command.getUsername().getLastName())
                .dateOfBirth(command.getDateOfBirth().toLocalDate())
                .gender(GenderModelMapper.toEntity(command.getGender()))
                .build();

        userProfileJpaRepository.save(profile);

        command.getTermsAndConditionsIds().forEach(
                termsAndConditionsId -> {
                    UserConsentEntity consents = UserConsentEntity.builder()
                            .user(user)
                            .termsAndConditions(termsAndConditionsJpaRepository.getReferenceById(termsAndConditionsId))
                            .consented(true)
                            .consentedAt(now)
                            .build();
                    userConsentJpaRepository.save(consents);
                }
        );

        return user.getId();
    }
}
