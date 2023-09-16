package me.choicore.likeapuppy.core.user.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * 사용자에게 부여된 권한 정보
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "granted_authority")
public class GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    private Instant grantedAt;


    /**
     * @param id        PK
     * @param userId    FK
     * @param authority Authority
     * @param grantedAt Granted At
     */
    @Builder
    public GrantedAuthority(
            final Long id,
            final Long userId,
            final Authority authority,
            final Instant grantedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.authority = authority;
        this.grantedAt = grantedAt;
    }
}
