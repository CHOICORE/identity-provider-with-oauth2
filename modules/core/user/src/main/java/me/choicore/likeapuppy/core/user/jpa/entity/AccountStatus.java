package me.choicore.likeapuppy.core.user.jpa.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * 계정 자격증명 정보 모델
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AccountStatus {

    @Transient
    private static final int DEFAULT_LOGIN_ATTEMPTS_LIMIT = 5;
    @Transient
    private static final int DEFAULT_PASSWORD_EXPIRATION_DAYS = 30;

    @Transient
    private int loginAttemptsLimit;

    @ColumnDefault("0")
    private int failedLoginAttempts;

    private Instant lastLoggedInAt;

    private Instant passwordExpirationAt;

    private boolean emailVerified;

    private Instant emailVerifiedAt;

    private boolean mobileVerified;

    private Instant mobileVerifiedAt;

    /**
     * @param failedLoginAttempts  로그인 실패 횟수
     * @param lastLoggedInAt       마지막 로그인 일시
     * @param passwordExpirationAt 패스워드 만료 일시
     * @param emailVerified        이메일 인증 여부
     * @param emailVerifiedAt      이메일 인증 일시
     * @param mobileVerified       휴대폰 인증 여부
     * @param mobileVerifiedAt     휴대폰 인증 일시
     */
    @Builder
    public AccountStatus(
            final int failedLoginAttempts,
            final Instant lastLoggedInAt,
            final Instant passwordExpirationAt,
            final boolean emailVerified,
            final Instant emailVerifiedAt,
            final boolean mobileVerified,
            final Instant mobileVerifiedAt
    ) {
        this.failedLoginAttempts = failedLoginAttempts;
        this.lastLoggedInAt = lastLoggedInAt;
        this.passwordExpirationAt = passwordExpirationAt;
        this.emailVerified = emailVerified;
        this.emailVerifiedAt = emailVerifiedAt;
        this.mobileVerified = mobileVerified;
        this.mobileVerifiedAt = mobileVerifiedAt;
    }

    public static AccountStatusBuilder withDefault() {
        return AccountStatus.builder()
                .lastLoggedInAt(null)
                .failedLoginAttempts(0)
                .passwordExpirationAt(addDaysToCurrentTime(DEFAULT_PASSWORD_EXPIRATION_DAYS))
                .emailVerified(false)
                .emailVerifiedAt(null)
                .mobileVerified(false)
                .mobileVerifiedAt(null);
    }

    public static AccountStatusBuilder withVerifiedCredentials() {
        Instant verifiedAt = Instant.now();
        return AccountStatus.builder()
                .lastLoggedInAt(null)
                .failedLoginAttempts(0)
                .passwordExpirationAt(addDaysToCurrentTime(DEFAULT_PASSWORD_EXPIRATION_DAYS))
                .emailVerified(true)
                .emailVerifiedAt(verifiedAt)
                .mobileVerified(true)
                .mobileVerifiedAt(verifiedAt);
    }

    public static AccountStatus createDefault() {
        return withDefault().build();
    }

    // TODO: common module 로 분리
    @Transient
    private static Instant addDaysToCurrentTime(final long days) {
        return Instant.now().plus(days, ChronoUnit.DAYS);
    }

    /**
     * 로그인 실패 횟수를 증가시킨다.
     */
    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts += 1;
    }

    /**
     * 로그인 성공 시 로그인 실패 시 자격증명 상태를 갱신한다.
     * <p>
     * 로그인 실패 횟수를 초기화하고 마지막 로그인 일시를 갱신한다.
     * </p>
     *
     * @param lastLoggedInAt 마지막 로그인 일시
     */
    public void markAsLoggedIn(Instant lastLoggedInAt) {
        this.lastLoggedInAt = lastLoggedInAt;
        this.failedLoginAttempts = 0;
    }

    public void markAsLoggedIn() {
        this.markAsLoggedIn(Instant.now());
    }

    /**
     * 로그인 실패 시 자격증명 상태를 갱신한다.
     * <p>
     * 로그인 실패 횟수를 증가시킨다.
     * </p>
     */
    public void markAsFailedToLogin() {
        this.incrementFailedLoginAttempts();
    }

    /**
     * 패스워드 만료 일시를 갱신한다.
     *
     * @param passwordExpirationAt 패스워드 만료 일시
     */
    public void updatePasswordExpirationAt(Instant passwordExpirationAt) {
        this.passwordExpirationAt = passwordExpirationAt;
    }

    /**
     * 패스워드 만료 일시를 갱신한다.
     * <p>
     * 패스워드 만료 일시를 현재 일시로부터 30일(패스워드 만료 일시 연장 기본 정책) 후로 갱신한다.
     * </p>
     */
    public void delayPasswordExpiration() {
        this.passwordExpirationAt = addDaysToCurrentTime(DEFAULT_PASSWORD_EXPIRATION_DAYS);
    }

    public void markAsEmailVerified() {
        this.emailVerified = true;
        this.emailVerifiedAt = Instant.now();
    }

    public void markAsMobileVerified() {
        this.mobileVerified = true;
        this.mobileVerifiedAt = Instant.now();
    }

    @Transient
    public boolean isDormant() {
        if (this.lastLoggedInAt == null) return false;
        return lastLoggedInAt.isBefore(Instant.now().minus(365, ChronoUnit.DAYS));
    }

    @Transient
    public boolean isLoginAttemptsExceeded(int loginAttemptsLimit) {
        this.loginAttemptsLimit = loginAttemptsLimit;
        return this.failedLoginAttempts >= loginAttemptsLimit;
    }
}
