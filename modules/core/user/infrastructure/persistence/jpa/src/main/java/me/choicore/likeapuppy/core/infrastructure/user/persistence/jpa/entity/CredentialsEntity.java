package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CredentialsEntity {

    private String password;
    @ColumnDefault("0")
    private final int failedLoginAttempts = 0;
    private Instant passwordExpirationAt;

    public CredentialsEntity(String password) {
        this.password = password;
    }
}
