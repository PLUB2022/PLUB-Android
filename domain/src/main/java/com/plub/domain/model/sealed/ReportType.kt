package com.plub.domain.model.sealed

sealed class ReportType {
    data class RecruitReport(val plubbingId : Int) : ReportType()
    data class AccountReport(val plubbingId: Int, val accountId : Int) : ReportType()
    data class FeedReport(val plubbingId : Int, val feedId : Int) : ReportType()
    data class TodoReport(val plubbingId: Int, val todoId : Int) : ReportType()
    data class ArchiveReport(val plubbingId: Int, val archiveId : Int) : ReportType()
    data class FeedCommentReport(val plubbingId: Int, val commentId : Int) : ReportType()
    data class NoticeCommentReport(val plubbingId: Int, val commentId : Int) : ReportType()
}
