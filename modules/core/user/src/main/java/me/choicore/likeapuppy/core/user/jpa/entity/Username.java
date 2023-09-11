package me.choicore.likeapuppy.core.user.jpa.entity;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {
    private String firstName;
    private String lastName;


    @Builder
    public Username(
            final String firstName,
            final String lastName
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
