package com.plub.domain.error

sealed class NoticeError: IndividualError() {

    companion object{
        private const val NOT_FOUND_ANNOUNCEMENT = 4010
        private const val NOT_FOUND_NOTICE = 8500
        private const val NOT_NOTICE_AUTHOR = 8510
        private const val DELETED_NOTICE = 8520

        fun make(code:Int) : NoticeError {
            return when(code) {
                NOT_FOUND_ANNOUNCEMENT -> NotFoundAnnouncement("앱 공지사항을 찾을 수 없음")
                NOT_FOUND_NOTICE -> NotFoundNotice("공지 id를 찾을 수 없음")
                NOT_NOTICE_AUTHOR -> NotNoticeAuthor("공지 작성자가 아님")
                DELETED_NOTICE -> DeletedNotice("삭제된 공지")
                else -> Common
            }
        }
    }

    object Common : NoticeError()
    data class NotFoundAnnouncement(val msg:String): NoticeError()
    data class NotFoundNotice(val msg:String): NoticeError()
    data class NotNoticeAuthor(val msg:String): NoticeError()
    data class DeletedNotice(val msg:String): NoticeError()
}
