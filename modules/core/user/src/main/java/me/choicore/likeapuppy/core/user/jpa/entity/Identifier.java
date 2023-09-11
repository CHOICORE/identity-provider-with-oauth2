package me.choicore.likeapuppy.core.user.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Identifier {
    @Column(unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 20)
    private String mobile;

    public Identifier(
            final String email,
            final String mobile
    ) {
        this.email = email;
        this.mobile = mobile;
    }
}
