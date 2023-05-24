package com.plub.domain.error

sealed class LoginError: IndividualError() {
    companion object {
        private const val NEED_SIGN_UP = 2050
        private const val FAIL_LOGIN = 9040
        private const val STOPPED_ACCOUNT = 2070
        private const val NOT_FOUND_REFRESH_TOKEN = 1040


        fun make(statusCode: Int): LoginError {
            return when(statusCode) {
                NEED_SIGN_UP -> NeedSignUp("회원가입 필요")
                FAIL_LOGIN -> FailLogin("로그인 실패")
                STOPPED_ACCOUNT -> StoppedAccount("정지된 유저")
                NOT_FOUND_REFRESH_TOKEN -> NotFoundRefreshToken("Refresh Token 값 없음")
                else -> Common
            }
        }
    }
    data class NeedSignUp(val msg:String): LoginError()
    data class FailLogin(val msg:String): LoginError()
    data class StoppedAccount(val msg:String): LoginError()
    data class NotFoundRefreshToken(val msg:String): LoginError()
    object Common: LoginError()
}