package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@Table(name = "authority", indexes = {@Index(name = "idx_authority_name", columnList = "name", unique = true)})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String scope;

    private String description;

    private Instant registeredAt;

    private Instant modifiedAt;

    /**
     * @param id           PK
     * @param name         권한 명
     * @param scope        권한 범위
     * @param description  권한 설명
     * @param registeredAt 등록 일시
     * @param modifiedAt   수정 일시
     */
    @Builder
    public AuthorityEntity(
            final Long id,
            final String name,
            final String scope,
            final String description,
            final Instant registeredAt,
            final Instant modifiedAt
    ) {
        this.id = id;
        this.name = name;
        this.scope = scope;
        this.description = description;
        this.registeredAt = registeredAt;
        this.modifiedAt = modifiedAt;
    }
}

