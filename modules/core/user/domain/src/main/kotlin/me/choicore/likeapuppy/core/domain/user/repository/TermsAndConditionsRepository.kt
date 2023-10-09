package me.choicore.likeapuppy.core.domain.user.repository

import me.choicore.likeapuppy.core.domain.user.model.TermsAndConditions

interface TermsAndConditionsRepository {

    fun find(usedOnly: Boolean = true): List<TermsAndConditions>

    fun findByIds(ids: List<Long>): List<TermsAndConditions>

    fun findByMandatoryOnly(): List<TermsAndConditions>
}
