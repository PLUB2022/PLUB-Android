package com.plub.domain.result

sealed class LoginFailure: IndividualFailure() {
    companion object {
        private const val NOT_FOUND_USER_ACCOUNT = 400
        private const val INVALIDED_ACCOUNT_CODE = 456

        fun make(statusCode: Int): LoginFailure {
            return when(statusCode) {
                NOT_FOUND_USER_ACCOUNT -> NotFoundUserAccount("계정을 찾지 못함")
                INVALIDED_ACCOUNT_CODE -> InvalidedAccount("잘못된 계정")
                else -> Common
            }
        }
    }
    data class NotFoundUserAccount(val msg:String): LoginFailure()
    data class InvalidedAccount(val msg:String): LoginFailure()
    object Common: LoginFailure()
}