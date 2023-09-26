package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authentication {

    @Embedded
    private Identifier identifier;

    @Embedded
    private Credentials credentials;

    private Instant lastLoggedInAt;

    private Instant registeredAt;
}
