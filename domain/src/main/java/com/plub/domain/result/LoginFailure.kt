package com.plub.domain.result

sealed class LoginFailure: IndividualFailure() {
    companion object {
        private const val INVALIDED_ACCOUNT_CODE = 456

        fun make(statusCode: Int): LoginFailure {
            return when(statusCode) {
                INVALIDED_ACCOUNT_CODE -> InvalidedAccount("잘못된 계정")
                else -> Common
            }
        }
    }
    data class InvalidedAccount(val msg:String): LoginFailure()
    object Common: LoginFailure()
}