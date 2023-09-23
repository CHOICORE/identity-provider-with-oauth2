package me.choicore.likeapuppy.core.domain

/**
 * Validation Interface
 *
 * Subclasses of this abstract class can implement the [validate] method to define their
 * own validation rules and conditions. The [validate] method should throw an
 * [IllegalArgumentException] with an appropriate error message if the validation fails.
 * The [validate] method is intended to validate a specific set of conditions based on the
 * implementation in each subclass.

 * @throws IllegalArgumentException if the validation fails with an appropriate error message.
 */

interface Validator {
    fun validate()
}

internal inline fun <T : Validator> validate(block: () -> T) {
    block().apply { this.validate() }
}

// abstract class Validator {
//
//    /**
//     * Validates the specified conditions based on the implementation in each subclass.
//     *
//     * Subclasses should override this method to define their own validation rules and conditions.
//     * If the validation fails, the method should throw an [IllegalArgumentException]
//     * with an appropriate error message.
//     */
//    @Throws(IllegalArgumentException::class)
//    protected abstract fun validate()
// }
