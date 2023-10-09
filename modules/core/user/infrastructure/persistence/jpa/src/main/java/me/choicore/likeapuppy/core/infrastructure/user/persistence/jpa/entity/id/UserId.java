package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.id;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId {

    @Column(name = "user_id", nullable = false, updatable = false)
    private long value;

    public UserId(final long value) {
        this.value = value;
    }

    public static UserId of(final long id) {
        return new UserId(id);
    }
}

