package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.base;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseInstantEntity {
    @CreatedDate
    private Instant registeredAt;
    @LastModifiedDate
    private Instant modifiedAt;
}
