package com.plub.domain.error

sealed class LoginError: IndividualError() {
    companion object {
        private const val NEED_SIGN_UP = 1050

        fun make(statusCode: Int): LoginError {
            return when(statusCode) {
                NEED_SIGN_UP -> NeedSignUp("회원가입 필요")
                else -> Common
            }
        }
    }
    data class NeedSignUp(val msg:String): LoginError()
    object Common: LoginError()
}