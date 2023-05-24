package com.plub.domain.error

sealed class AccountError: IndividualError() {
    companion object {
        private const val NOT_FOUND_ACCOUNT = 2050
        private const val REVOKE_FAIL = 9040
        private const val NOT_FOUND_CATEGORY = 3000


        fun make(statusCode: Int): AccountError {
            return when(statusCode) {
                NOT_FOUND_ACCOUNT -> NotFoundAccount("회원이 존재하지 않음")
                REVOKE_FAIL -> RevokeFail("탈퇴 요청 오류")
                NOT_FOUND_CATEGORY -> NotFoundCategory("category를 찾을 수 없음")
                else -> Common
            }
        }
    }
    data class NotFoundAccount(val msg:String): AccountError()
    data class RevokeFail(val msg:String): AccountError()
    data class NotFoundCategory(val msg : String) : AccountError()
    object Common: AccountError()
}