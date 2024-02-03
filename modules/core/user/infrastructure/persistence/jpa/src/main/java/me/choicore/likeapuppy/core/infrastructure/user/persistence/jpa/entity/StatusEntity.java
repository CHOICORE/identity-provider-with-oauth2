package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

/**
 * 사용자 상태 열거형 상수 정의
 * PENDING: 대기
 * ACTIVE: 활성화
 * INACTIVE: 비활성화
 * DELETED: 삭제
 */
public enum StatusEntity {

    /**
     * 사용자 상태: 대기
     */
    PENDING,

    /**
     * 사용자 상태: 활성화
     */
    ACTIVE,

    /**
     * 사용자 상태: 비활성화
     */
    INACTIVE,

    /**
     * 사용자 상태: 삭제
     */
    DELETED
}
