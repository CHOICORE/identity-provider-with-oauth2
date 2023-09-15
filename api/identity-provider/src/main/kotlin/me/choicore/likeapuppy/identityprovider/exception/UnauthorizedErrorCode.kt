package me.choicore.likeapuppy.identityprovider.exception

enum class UnauthorizedErrorCode(val code: String, val type: UnauthorizedErrorType) {

    INTERNAL_ERROR(
        code = "0001",
        type = UnauthorizedErrorType.INTERNAL_ERROR,
    ),

    ACCOUNT_NOT_FOUND(
        code = "0002",
        type = UnauthorizedErrorType.INVALID_CREDENTIALS,
    ),

    INVALID_PASSWORD(
        code = "0003",
        type = UnauthorizedErrorType.INVALID_CREDENTIALS,
    ),

    LOGIN_ATTEMPTS_EXCEEDED(
        code = "0004",
        type = UnauthorizedErrorType.LOGIN_ATTEMPTS_EXCEEDED,
    ),

    INACTIVE_ACCOUNT(
        code = "0005",
        type = UnauthorizedErrorType.INACTIVE_ACCOUNT,
    ),

    EXPIRED_ACCOUNT_PASSWORD(
        code = "0006",
        type = UnauthorizedErrorType.EXPIRED_ACCOUNT_PASSWORD,
    ),

    LOCKED_ACCOUNT(
        code = "0007",
        type = UnauthorizedErrorType.LOCKED_ACCOUNT,
    ),

    INVALID_TOKEN(
        code = "0008",
        type = UnauthorizedErrorType.INVALID_TOKEN,
    ),

    EXPIRED_TOKEN(
        code = "0009",
        type = UnauthorizedErrorType.EXPIRED_TOKEN,
    ),

    UNKNOWN_ERROR(
        code = "-999",
        type = UnauthorizedErrorType.UNKNOWN_ERROR,
    ),
}

enum class UnauthorizedErrorType {

    INVALID_CREDENTIALS,

    LOGIN_ATTEMPTS_EXCEEDED,

    INACTIVE_ACCOUNT,

    EXPIRED_ACCOUNT_PASSWORD,

    INVALID_TOKEN,

    EXPIRED_TOKEN,

    INTERNAL_ERROR,

    LOCKED_ACCOUNT,

    UNKNOWN_ERROR,
}
