package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private AuthenticationEntity authentication;

    @OneToOne(fetch = FetchType.LAZY)
    private UserProfileEntity profile;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserGrantedAuthorityEntity> grantedAuthorities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserConsentEntity> agreements = new ArrayList<>();

    @Embedded
    private VerificationEntity verification;

    @Builder
    private UserEntity(
            final AuthenticationEntity authentication,
            final UserProfileEntity profile,
            final List<UserGrantedAuthorityEntity> grantedAuthorities,
            final List<UserConsentEntity> agreements,
            final VerificationEntity verification
    ) {
        this.authentication = authentication;
        this.profile = profile;
        this.grantedAuthorities = grantedAuthorities;
        this.agreements = agreements;
        this.verification = verification;
    }
}
