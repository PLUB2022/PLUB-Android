package com.plub.domain.error

sealed class GatheringError: IndividualError() {

    companion object{
        private const val NOT_FOUND_PLUBBING = 6000
        private const val NOT_JOINED_PLUBBING = 6010
        private const val NOT_HOST = 6020
        private const val ALREADY_FINISH = 6030
        private const val NOT_MEMBER_PLUBBING = 6040
        private const val NOT_FOUND_SUB_CATEGORY = 6050
        private const val HOST_CANNOT_APPLY = 6060
        private const val NOT_FOUND_QUESTION = 6070
        private const val NOT_APPLIED_APPLICANT = 6080
        private const val ALREADY_APPLIED = 6090
        private const val ALREADY_ACCEPTED = 6100
        private const val ALREADY_REJECTED = 6110
        private const val FULL_MEMBER_PLUBBING = 6120
        private const val NOT_FOUND_RECRUIT = 6130
        private const val ALREADY_RECRUIT_DONE = 6140
        private const val LIMIT_MAX_PLUBBING = 6150
        private const val LIMIT_PULL_UP = 6160

        fun make(code:Int) : GatheringError {
            return when(code) {
                NOT_FOUND_PLUBBING -> NotFoundPlubbing("모임이 존재하지 않음")
                NOT_JOINED_PLUBBING -> NotJoinedPlubbing("모임에 대한 접근 권한 없음")
                NOT_HOST -> NotHost("호스트가 아님")
                ALREADY_FINISH -> AlreadyFinish("모임이 끝난 상태")
                NOT_MEMBER_PLUBBING -> NotMemberPlubbing("모임의 멤버가 아님")
                NOT_FOUND_SUB_CATEGORY -> NotFoundSubCategory("카테고리id를 찾을 수 없음")
                HOST_CANNOT_APPLY -> HostCannotApply("호스트는 자기 자신 모임에 지원 불가")
                NOT_FOUND_QUESTION -> NotFoundQuestion("해당 질문을 찾을 수 없음")
                NOT_APPLIED_APPLICANT -> NotAppliedApplicant("지원자가 아님")
                ALREADY_APPLIED -> AlreadyApplied("이미 지원한 모집글")
                ALREADY_ACCEPTED -> AlreadyAccepted("이미 승난한 지원서")
                ALREADY_REJECTED -> AlreadyRejected("이미 거절한 지원서")
                FULL_MEMBER_PLUBBING -> FullMemberPlubbing("최대 멤버를 초과함")
                NOT_FOUND_RECRUIT -> NotFoundRecruit("모집글을 찾을 수 없음")
                ALREADY_RECRUIT_DONE -> AlreadyRecruitDone("이미 모집이 종료된 경우")
                LIMIT_MAX_PLUBBING -> LimitMaxPlubbing("이미 3개 이상의 모임 활동중")
                LIMIT_PULL_UP -> LimitPullUp("끌올 최대 횟수 제한")
                else -> Common
            }
        }
    }

    data class NotFoundPlubbing(val msg:String): GatheringError()
    data class NotJoinedPlubbing(val msg:String): GatheringError()
    data class NotHost(val msg:String): GatheringError()
    data class AlreadyFinish(val msg:String): GatheringError()
    data class NotMemberPlubbing(val msg:String): GatheringError()
    data class NotFoundSubCategory(val msg:String): GatheringError()
    data class HostCannotApply(val msg:String): GatheringError()
    data class NotFoundQuestion(val msg:String): GatheringError()
    data class NotAppliedApplicant(val msg:String): GatheringError()
    data class AlreadyApplied(val msg:String): GatheringError()
    data class AlreadyAccepted(val msg:String): GatheringError()
    data class AlreadyRejected(val msg:String): GatheringError()
    data class FullMemberPlubbing(val msg:String): GatheringError()
    data class NotFoundRecruit(val msg:String): GatheringError()
    data class AlreadyRecruitDone(val msg:String): GatheringError()
    data class LimitMaxPlubbing(val msg:String): GatheringError()
    data class LimitPullUp(val msg:String): GatheringError()

    object Common : GatheringError()
}
