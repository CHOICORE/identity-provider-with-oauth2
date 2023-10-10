package me.choicore.likeapuppy.core.domain.user.service

import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.service.internal.AuthorityQueryProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.TermsAndConditionsProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.UserCommandProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.UserQueryProcessor

/**
 * 사용자 등록을 처리하는 서비스입니다.
 *
 * @property userCommandProcessor 사용자 관련 명령을 처리하는 서비스입니다.
 * @property userQueryProcessor 사용자 관련 쿼리를 처리하는 서비스입니다.
 * @property termsAndConditionsProcessor 이용 약관을 검증하는 서비스입니다.
 * @property authorityQueryProcessor 권한 관련 쿼리를 처리하는 서비스입니다.
 */
class UserRegistrationService(
    private val userCommandProcessor: UserCommandProcessor,
    private val userQueryProcessor: UserQueryProcessor,
    private val termsAndConditionsProcessor: TermsAndConditionsProcessor,
    private val authorityQueryProcessor: AuthorityQueryProcessor,
) {

    /**
     * 제공된 명령 객체를 기반으로 새 사용자를 등록합니다.
     *
     * 이 함수는 제공된 객체를 검증한 후, [delegateRegistration] 메서드를 호출하여 등록 과정을 위임합니다.
     *
     * @param command 등록에 필요한 모든 데이터를 포함하는 명령 객체.
     *
     * @return 새롭게 등록된 사용자의 ID를 Long 값으로 반환합니다.
     */
    fun register(command: RegisterUserCommand): Long {
        userQueryProcessor.validateUserIdentifier(command.email, command.phoneNumber)
        termsAndConditionsProcessor.validateTermsAndConditionsIds(command.termsAndConditionsIds)
        return delegateRegistration(command)
    }

    /**
     * 받은 명령 객체의 유형에 따라 등록 과정을 위임합니다.
     *
     * 이 함수는 [RegisterUserCommand] 객체의 유형을 확인하고 [RegisterUserCommand.ContainsAuthorityNames] 인스턴스인지
     * [RegisterUserCommand.ContainsAuthorityIds] 인스턴스인지에 따라 적절한 등록 메서드를 호출합니다.
     *
     * @param command 등록에 필요한 모든 데이터를 포함하는 객체. 두 가지 유형 중 하나일 수 있습니다:
     * [RegisterUserCommand.ContainsAuthorityNames] 또는 [RegisterUserCommand.ContainsAuthorityIds].
     *
     * @return 신규 사용자의 ID를 반환합니다.
     */
    private fun delegateRegistration(command: RegisterUserCommand): Long {
        return when (command) {
            is RegisterUserCommand.ContainsAuthorityNames -> registerContainsAuthorityNamesToIds(command)
            is RegisterUserCommand.ContainsAuthorityIds -> registerContainsAuthorityIds(command)
        }
    }

    /**
     * 권한 명이 포함된 객체로 사용자를 등록하고, 권한 이름을 ID로 변환합니다.
     *
     * 이 함수는 권한 명을 ID로 변환하여 새로운 [RegisterUserCommand.ContainsAuthorityIds] 인스턴스를 생성합니다.
     * 그런 다음 [UserCommandProcessor.register] 메서드를 호출합니다.
     *
     * @param command 권한 이름 정보가 포함된 특정 유형의 사용자 등록 객체입니다.
     * @see [RegisterUserCommand.ContainsAuthorityNames].
     *
     * @return 신규 사용자의 ID를 반환합니다.
     */
    private fun registerContainsAuthorityNamesToIds(
        command: RegisterUserCommand.ContainsAuthorityNames,
    ): Long {
        return userCommandProcessor.register(
            command = command.toWithAuthorityIds(authorities = getAuthorityIds(command.authorityNames)),
        )
    }

    /**
     * 권한 ID가 포함된 객체로 권한 ID를 검증하고, 사용자를 등록합니다.
     *
     * 이 함수는 [AuthorityQueryProcessor.validateAuthorityIds] 메서드를 호출하여 권한 ID를 검증합니다.
     * 그런 다음, [UserCommandProcessor.register] 메서드를 호출합니다.
     *
     * @param command 권한 ID 정보가 포함된 특정 유형의 사용자 등록 객체입니다.
     * @see [RegisterUserCommand.ContainsAuthorityIds].
     *
     * @return 신규 사용자의 ID를 반환합니다.
     */
    private fun registerContainsAuthorityIds(
        command: RegisterUserCommand.ContainsAuthorityIds,
    ): Long {
        authorityQueryProcessor.validateAuthorityIds(command.authorityIds)
        return userCommandProcessor.register(command = command)
    }

    /**
     * 권한 명 목록에 해당하는 권한 ID 목록을 반환합니다.
     *
     * 이 함수는 [AuthorityQueryProcessor.getAuthorityByAuthorityNames] 메서드를 호출하여 해당 권한 객체를 가져온 후, ID로 변환합니다.
     *
     * @param authorityNames ID로 변환될 권한 명 목록입니다.
     *
     * @return 제공된 권한 이름에 해당하는 ID 목록을 반환합니다.
     */
    private fun getAuthorityIds(authorityNames: List<AuthorityNames>): List<Long> =
        authorityQueryProcessor.getAuthorityByAuthorityNames(authorityNames).map { it.id }
}

private fun RegisterUserCommand.ContainsAuthorityNames.toWithAuthorityIds(
    authorities: List<Long>,
): RegisterUserCommand.ContainsAuthorityIds = RegisterUserCommand.ContainsAuthorityIds(
    email = this.email,
    password = this.password,
    username = this.username,
    dateOfBirth = this.dateOfBirth,
    gender = this.gender,
    phoneNumber = this.phoneNumber,
    termsAndConditionsIds = this.termsAndConditionsIds,
    authorityIds = authorities,
)
