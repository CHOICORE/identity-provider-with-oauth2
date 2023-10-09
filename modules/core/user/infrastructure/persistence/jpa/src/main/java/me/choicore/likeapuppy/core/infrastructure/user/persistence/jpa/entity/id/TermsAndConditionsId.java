package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsAndConditionsId {

    @Column(name = "terms_and_conditions_id")
    private long value;

    public TermsAndConditionsId(final long value) {
        this.value = value;
    }

    public static TermsAndConditionsId of(final long id) {
        return new TermsAndConditionsId(id);
    }
}
