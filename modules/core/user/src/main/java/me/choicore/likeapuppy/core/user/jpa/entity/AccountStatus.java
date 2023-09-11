package me.choicore.likeapuppy.core.user.jpa.entity;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AccountStatus {

	@ColumnDefault("0")
	private int failedLoginAttempts;

	private Instant lastLoggedInAt;

	private Instant lastPasswordModifiedAt;

	private boolean emailVerified;

	private Instant emailVerifiedAt;

	private boolean mobileVerified;

	private Instant mobileVerifiedAt;

	public AccountStatus(
			final int failedLoginAttempts,
			final Instant lastLoggedInAt,
			final Instant lastPasswordModifiedAt,
			final boolean emailVerified,
			final Instant emailVerifiedAt,
			final boolean mobileVerified,
			final Instant mobileVerifiedAt
	) {
		this.failedLoginAttempts = failedLoginAttempts;
		this.lastLoggedInAt = lastLoggedInAt;
		this.lastPasswordModifiedAt = lastPasswordModifiedAt;
		this.emailVerified = emailVerified;
		this.emailVerifiedAt = emailVerifiedAt;
		this.mobileVerified = mobileVerified;
		this.mobileVerifiedAt = mobileVerifiedAt;
	}
}
