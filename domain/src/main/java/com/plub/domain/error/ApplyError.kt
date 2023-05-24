package com.plub.domain.error

sealed class ApplyError: IndividualError() {
    companion object {
        private const val NOT_FOUND_USER = 2050
        private const val HOST_APPLY = 6020
        private const val NOT_HOST = 6030
        private const val ALREADY_ACCEPTED = 6070
        private const val ALREADY_REJECTED = 6080
        private const val NOT_FOUND_PLUBBING = 6010
        private const val FULL_MEMBER = 6120
        private const val ALREADY_APPLIED = 6060
        private const val ALREADY_DONE = 6140
        private const val LIMIT_MAX_PLUBBING = 6150

        fun make(statusCode: Int): ApplyError {
            return when(statusCode) {
                NOT_FOUND_USER -> NotFoundUser("유저를 찾을 수 없음")
                HOST_APPLY -> HostApply("호스트 지원 불가")
                NOT_HOST -> NotHost("권한이 없음")
                ALREADY_REJECTED ->AlreadyRejected("이미 거절한 유저")
                ALREADY_ACCEPTED -> AlreadyAccepted("이미 승낙한 멤버")
                NOT_FOUND_PLUBBING -> NotFoundPlubbing("모임을 찾을 수 없음")
                FULL_MEMBER -> FullMember("모임 최대 인원을 넘음")
                ALREADY_APPLIED -> AlreadyApplied("이미 지원한 모임")
                ALREADY_DONE -> AlreadyDone("모집이 끝난 모임")
                LIMIT_MAX_PLUBBING -> LimitMaxPlubbing("모임3개 신청 불가능")
                else -> Common
            }
        }
    }
    data class NotHost(val msg: String) : ApplyError()
    data class AlreadyAccepted(val msg: String) : ApplyError()
    data class AlreadyRejected(val msg: String) : ApplyError()
    data class NotFoundPlubbing(val msg: String) : ApplyError()
    data class FullMember(val msg: String) : ApplyError()
    data class NotFoundUser(val msg:String): ApplyError()
    data class HostApply(val msg:String): ApplyError()
    data class AlreadyApplied(val msg:String): ApplyError()
    data class AlreadyDone(val msg:String): ApplyError()
    data class LimitMaxPlubbing(val msg:String): ApplyError()
    object Common: ApplyError()
}