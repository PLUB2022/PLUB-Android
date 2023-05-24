package com.plub.domain.error

sealed class RecruitError: IndividualError() {
    companion object {
        private const val NOT_FOUND_RECRUIT = 6010
        private const val NOT_FOUND_ACCOUNT = 2050
        private const val NOT_HOST = 6030
        private const val FORBIDDEN_GUEST = 6020
        private const val LIMIT_PULL_UP = 6160


        fun make(statusCode: Int): RecruitError {
            return when(statusCode) {
                NOT_FOUND_RECRUIT -> NotFoundRecruit("plubbing을 찾을 수 없음")
                NOT_FOUND_ACCOUNT -> NotFoundAccount("회원을 찾을 수 없음")
                LIMIT_PULL_UP -> LimitPullUp("끌올 최대 2회 초과 불가능")
                NOT_HOST -> NotHost("모임 호스트가 아님")
                FORBIDDEN_GUEST -> ForbiddenGuest("호스트가 아닌 사람이 호출")
                else -> Common
            }
        }
    }
    data class NotFoundRecruit(val msg:String): RecruitError()
    data class NotFoundAccount(val msg:String): RecruitError()
    data class LimitPullUp(val msg: String) : RecruitError()
    data class NotHost(val msg : String) : RecruitError()
    data class ForbiddenGuest(val msg : String) : RecruitError()
    object Common: RecruitError()
}