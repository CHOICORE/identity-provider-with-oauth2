package me.choicore.likeapuppy.core.user.jpa.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Credential {

    @Embedded
    private Identifier identifier;

    private String password;

    public Credential(
            final Identifier identifier,
            final String password
    ) {
        this.identifier = identifier;
        this.password = password;
    }
}
