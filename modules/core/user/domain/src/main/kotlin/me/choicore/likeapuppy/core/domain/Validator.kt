package me.choicore.likeapuppy.core.domain

/**
 * 유효성 검사 인터페이스
 *
 * 이 추상 클래스의 하위 클래스는 [validate] 메서드를 구현하여 자체 유효성 검사 규칙과 조건을 정의할 수 있습니다.
 * [validate] 메서드는 유효성 검사가 실패하면 적절한 오류 메시지와 함께 [IllegalArgumentException]을 던져야 합니다.
 * [validate] 메서드는 각 하위 클래스에서의 구현에 따라 특정한 조건 집합을 검증하기 위해 사용됩니다.
 *
 * @throws IllegalArgumentException 유효성 검사가 실패하면 적절한 오류 메시지와 함께 발생합니다.
 */
interface Validator {
    fun validate()
}

internal inline fun <T : Validator> validate(block: () -> T) {
    block().apply { this.validate() }
}
