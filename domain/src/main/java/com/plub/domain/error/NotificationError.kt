package com.plub.domain.error

sealed class NotificationError: IndividualError() {

    companion object{
        private const val FAIL_FCM_ACCESS_GET = 4500
        private const val FAIL_PUSH_MESSAGE = 4510
        private const val FAIL_PARSING_MESSAGE = 4520
        private const val NOT_FOUND_NOTIFICATION = 4530

        fun make(code:Int) : NotificationError {
            return when(code) {
                FAIL_FCM_ACCESS_GET -> FailFcmAccessGet("엑세스 토큰 받기 실패")
                FAIL_PUSH_MESSAGE -> FailPushMessage("FCM 메세지 전송 실패")
                FAIL_PARSING_MESSAGE -> FailParsingMessage("FCM메세지 파싱 실패")
                NOT_FOUND_NOTIFICATION -> NotFoundNotification("유저의 알림을 찾을 수 없음")
                else -> Common
            }
        }
    }

    data class FailFcmAccessGet(val msg:String): NotificationError()
    data class FailPushMessage(val msg:String): NotificationError()
    data class FailParsingMessage(val msg:String): NotificationError()
    data class NotFoundNotification(val msg:String): NotificationError()
    object Common : NotificationError()
}
