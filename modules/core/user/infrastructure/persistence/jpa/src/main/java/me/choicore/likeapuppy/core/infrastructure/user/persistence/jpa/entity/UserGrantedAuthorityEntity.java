package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "user_granted_authority")
public class UserGrantedAuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private AuthorityEntity authorityEntity;

    private Instant grantedAt;


    /**
     * @param id        PK
     * @param userId    FK
     * @param authorityEntity Authority
     * @param grantedAt Granted At
     */
    @Builder
    public UserGrantedAuthorityEntity(
            final Long id,
            final Long userId,
            final AuthorityEntity authorityEntity,
            final Instant grantedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.authorityEntity = authorityEntity;
        this.grantedAt = grantedAt;
    }
}
