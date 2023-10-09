package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuthenticationEntity {

    @Embedded
    private UserIdentifierEntity identifier;

    @Embedded
    private CredentialsEntity credentials;

    private Instant lastLoggedInAt;

    private Instant registeredAt;

    public AuthenticationEntity(
            UserIdentifierEntity identifier,
            CredentialsEntity credentials
    ) {
        this.identifier = identifier;
        this.credentials = credentials;
    }


    public boolean isAccountNonExpired() {

        return true;
    }

    public boolean isCredentialsNonExpired() {

        return true;
    }

    public boolean isAccountNonLocked() {

        return true;
    }
}
