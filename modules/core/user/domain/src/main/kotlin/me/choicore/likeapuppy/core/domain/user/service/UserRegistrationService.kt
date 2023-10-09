package me.choicore.likeapuppy.core.domain.user.service

import me.choicore.likeapuppy.core.domain.user.command.RegisterUserCommand
import me.choicore.likeapuppy.core.domain.user.model.constant.AuthorityNames
import me.choicore.likeapuppy.core.domain.user.service.internal.AuthorityQueryProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.TermsAndConditionsProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.UserCommandProcessor
import me.choicore.likeapuppy.core.domain.user.service.internal.UserQueryProcessor

/**
 * A processor class for user registration.
 *
 * @property userCommandProcessor Service that handles user-related commands.
 * @property userQueryProcessor Service that handles user-related queries.
 * @property termsAndConditionsProcessor Service that validates terms and conditions.
 * @property authorityQueryProcessor Service that handles authority-related queries.
 */
class UserRegistrationService(
    private val userCommandProcessor: UserCommandProcessor,
    private val userQueryProcessor: UserQueryProcessor,
    private val termsAndConditionsProcessor: TermsAndConditionsProcessor,
    private val authorityQueryProcessor: AuthorityQueryProcessor,
) {

    /**
     * Registers a new user with the provided command data.
     *
     * This function first validates the provided terms and conditions IDs, then delegates the registration process
     * depending on the type of command received (WithAuthorityNames or WithAuthorityIds).
     *
     * @param command The command object containing all necessary data for registration. Can be one of two types:
     * [RegisterUserCommand.WithAuthorityNames] or [RegisterUserCommand.WithAuthorityIds].
     *
     * @return The ID of the newly registered user as a Long value.
     */
    fun register(command: RegisterUserCommand): Long {
        userQueryProcessor.validateUserIdentifier(command.email, command.phoneNumber)
        termsAndConditionsProcessor.validateTermsAndConditionsIds(command.termsAndConditionsIds)
        return delegate(command)
    }

    /**
     * Delegates the registration process depending on the type of received command object.
     *
     * This function checks the type of the command object and calls the appropriate register method
     * based on whether it's an instance of [RegisterUserCommand.WithAuthorityNames] or [RegisterUserCommand.WithAuthorityIds].
     *
     * @param command The command object containing all necessary data for registration. Can be one of two types:
     * [RegisterUserCommand.WithAuthorityNames] or [RegisterUserCommand.WithAuthorityIds].
     *
     *@return The ID of the newly registered user as a Long value.
     */
    private fun delegate(command: RegisterUserCommand): Long {
        return when (command) {
            is RegisterUserCommand.WithAuthorityNames -> register(command)
            is RegisterUserCommand.WithAuthorityIds -> register(command)
        }
    }

    /**
     * Registers a new user using an Authority Names-based registration command.
     *
     * This function converts authority names into IDs, and then uses these IDs to create a new [RegisterUserCommand.WithAuthorityIds] instance.
     * The new command is then passed to the userService's register method for user registration.
     *
     * @param command A specific type of Register User Command which contains Authority Names information.
     *
     * @return The ID of the newly registered user as a Long value.
     */
    private fun register(
        command: RegisterUserCommand.WithAuthorityNames,
    ): Long {
        return userCommandProcessor.register(
            command = command.toWithAuthorityIds(authorities = getAuthorityIds(command.authorities)),
        )
    }

    /**
     * Registers a new user using an Authority IDs-based registration command.
     *
     * This function first validates the provided authority ids. After validation, it calls userService's register method with the original command data.
     *
     * @param command A specific type of Register User Command which contains Authority Ids information.
     *
     * @return The ID of the newly registered user as a Long value.
     */
    private fun register(
        command: RegisterUserCommand.WithAuthorityIds,
    ): Long {
        authorityQueryProcessor.validateAuthorityIds(command.authorityIds)
        return userCommandProcessor.register(command = command)
    }

    /**
     * Converts a list of authority names into a list of corresponding IDs.
     *
     * This function calls the authorityService's getAuthorities method to fetch the corresponding Authority objects,
     * and then maps them to their IDs.
     *
     * @param authorityNames A list of AuthorityNames to be converted into IDs.
     *
     * @return A list of IDs corresponding to the provided authority names.
     */
    private fun getAuthorityIds(authorityNames: List<AuthorityNames>): List<Long> =
        authorityQueryProcessor.getAuthorities(authorityNames).map { it.id }
}

private fun RegisterUserCommand.WithAuthorityNames.toWithAuthorityIds(
    authorities: List<Long>,
): RegisterUserCommand.WithAuthorityIds = RegisterUserCommand.WithAuthorityIds(
    email = this.email,
    password = this.password,
    username = this.username,
    dateOfBirth = this.dateOfBirth,
    gender = this.gender,
    phoneNumber = this.phoneNumber,
    termsAndConditionsIds = this.termsAndConditionsIds,
    authorityIds = authorities,
)
