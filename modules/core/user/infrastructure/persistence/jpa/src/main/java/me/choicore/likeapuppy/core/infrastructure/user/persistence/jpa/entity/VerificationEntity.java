package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationEntity {

    private boolean emailVerified;

    private Instant emailVerifiedAt;

    private boolean phoneNumberVerified;

    private Instant phoneNumberVerifiedAt;

    public static VerificationEntity Unverified() {
        return new VerificationEntity();
    }
}
