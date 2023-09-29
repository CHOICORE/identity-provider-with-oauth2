package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserIdentifierEntity {
    @Column(unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    public UserIdentifierEntity(
            final String email,
            final String phoneNumber
    ) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
