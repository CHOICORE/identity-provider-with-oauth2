package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "authority")

public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String scope;

    private String description;

    /**
     * @param id          PK
     * @param name        권한 명
     * @param scope       권한 범위
     * @param description 권한 설명
     */
    @Builder
    public Authority(
            final Long id,
            final String name,
            final String scope,
            final String description
    ) {
        this.id = id;
        this.name = name;
        this.scope = scope;
        this.description = description;
    }
}
