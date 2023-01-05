package com.plub.domain.error

sealed class LoginError: IndividualError() {
    companion object {
        private const val NOT_FOUND_USER_ACCOUNT = 400
        private const val INVALIDED_ACCOUNT_CODE = 456

        fun make(statusCode: Int): LoginError {
            return when(statusCode) {
                NOT_FOUND_USER_ACCOUNT -> NotFoundUserAccount("계정을 찾지 못함")
                INVALIDED_ACCOUNT_CODE -> InvalidedAccount("잘못된 계정")
                else -> Common
            }
        }
    }
    data class NotFoundUserAccount(val msg:String): LoginError()
    data class InvalidedAccount(val msg:String): LoginError()
    object Common: LoginError()
}