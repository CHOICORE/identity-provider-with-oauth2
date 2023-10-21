package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.TermsAndConditions
import me.choicore.likeapuppy.core.domain.user.repository.TermsAndConditionsQueryRepository

class TermsAndConditionsProcessor(
    private val repository: TermsAndConditionsQueryRepository,
) {
    /**
     * 약관 ID 목록을 검증합니다.
     *
     * 이 함수는 약관 ID 목록으로 [TermsAndConditionsQueryRepository.find] 메서드를 호출하여 해당 [TermsAndConditions] 객체 목록을 가져온 후, 요청한 ID 목록을 검증합니다.
     *
     * 1. 약관 ID 목록이 비어있는지 검증합니다.
     * 2. 약관 ID가 유효한지 검증합니다.
     * 3. 필수 약관이 동의되었는지 검증합니다.
     *
     * @param termsAndConditionsIds 약관 ID 목록
     * @throws IllegalArgumentException 약관 ID 목록이 비어있거나, 약관 ID가 유효하지 않거나, 필수 약관이 동의되지 않은 경우 발생합니다.
     */
    fun validateTermsAndConditionsIds(termsAndConditionsIds: List<Long>) {
        check(termsAndConditionsIds.isNotEmpty()) {
            "Terms and conditions ids is null"
        }
        val storedTermsAndConditions: List<TermsAndConditions> = repository.find(true)

        check(storedTermsAndConditions.map { it.id }.containsAll(termsAndConditionsIds)) {
            "Terms and conditions id is invalid"
        }

        val requiredTermsAndConditionsIds: List<Long> =
            storedTermsAndConditions
                .filter { it.mandatory }
                .map { it.id }

        check(requiredTermsAndConditionsIds.containsAll(termsAndConditionsIds)) {
            "Mandatory terms and conditions is not consented"
        }
    }
}
