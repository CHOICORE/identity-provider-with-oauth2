package me.choicore.likeapuppy.core.domain.user.service.internal

import me.choicore.likeapuppy.core.domain.user.model.TermsAndConditions
import me.choicore.likeapuppy.core.domain.user.repository.TermsAndConditionsRepository

class TermsAndConditionsProcessor(
    private val termsAndConditionsRepository: TermsAndConditionsRepository,
) {

    /**
     * Check if all terms and conditions are exists and mandatory.
     */
    fun validateTermsAndConditionsIds(termsAndConditionsIds: List<Long>) {
        check(termsAndConditionsIds.isNotEmpty()) {
            "Terms and conditions ids is null"
        }
        val storedTermsAndConditions: List<TermsAndConditions> = termsAndConditionsRepository.find(true)

        check(storedTermsAndConditions.map { it.id }.containsAll(termsAndConditionsIds)) {
            "Terms and conditions id is invalid"
        }

        val requiredTermsAndConditionsIds: List<Long> = storedTermsAndConditions
            .filter { it.mandatory }
            .map { it.id }

        check(requiredTermsAndConditionsIds.containsAll(termsAndConditionsIds)) {
            "Mandatory terms and conditions is not consented"
        }
    }
}
