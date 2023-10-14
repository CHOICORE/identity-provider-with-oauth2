package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_consent")
public class UserConsentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_consent_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terms_and_conditions_id")
    private TermsAndConditionsEntity termsAndConditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private boolean consented;

    @CreatedDate
    private Instant consentedAt;

    @Builder
    public UserConsentEntity(
            final Long id,
            final TermsAndConditionsEntity termsAndConditions,
            final UserEntity user,
            final boolean consented,
            final Instant consentedAt
    ) {
        this.id = id;
        this.termsAndConditions = termsAndConditions;
        this.user = user;
        this.consented = consented;
        this.consentedAt = consentedAt;
    }
}
