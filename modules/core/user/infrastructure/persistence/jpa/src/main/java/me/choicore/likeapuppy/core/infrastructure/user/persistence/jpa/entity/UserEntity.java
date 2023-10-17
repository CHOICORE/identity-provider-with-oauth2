package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    private StatusEntity status;

    @Embedded
    private AuthenticationEntity authentication;

    private String password;

    private String nickname;

    private String firstName;

    private String middleName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderEntity gender;

    private LocalDate dateOfBirth;

    private String aboutMe;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserGrantedAuthorityEntity> grantedAuthorities = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserConsentedTermsAndConditionsEntity> consentedTermsAndConditions = new ArrayList<>();

    @Embedded
    private VerificationEntity verification;

    private Instant registeredAt;

    @Builder
    private UserEntity(
            final AuthenticationEntity authentication,
            final List<UserGrantedAuthorityEntity> grantedAuthorities,
            final List<UserConsentedTermsAndConditionsEntity> consentedTermsAndConditions,
            final VerificationEntity verification
    ) {
        this.authentication = authentication;
        this.grantedAuthorities = grantedAuthorities;
        this.consentedTermsAndConditions = consentedTermsAndConditions;
        this.verification = verification;
    }
//
//    public Profile.PersonalName getPersonalName() {
//        return ProfileModelMapper.getPersonalName(
//                this.firstName,
//                this.middleName,
//                this.lastName
//        );
//    }
}
