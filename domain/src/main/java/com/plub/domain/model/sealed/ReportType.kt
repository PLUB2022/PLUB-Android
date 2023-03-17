package com.plub.domain.model.sealed

sealed class ReportType {
    data class PlubbingReport(val plubbingId : Int) : ReportType()
    data class FeedReport(val plubbingId : Int, val feedId : Int) : ReportType()
    data class FeedCommentReport(val plubbingId: Int, val feedId: Int, val commentId : Int) : ReportType()
    data class NoticeReport(val plubbingId: Int, val noticeId : Int) : ReportType()
    data class NoticeCommentReport(val plubbingId: Int, val noticeId: Int, val commentId : Int) : ReportType()
    data class ArchiveReport(val plubbingId: Int, val archiveId : Int) : ReportType()
    data class ProfileReport(val accountId : Int) : ReportType()
}
