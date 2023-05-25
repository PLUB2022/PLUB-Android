package com.plub.domain.error

sealed class ReportError: IndividualError() {

    companion object{
        private const val NOT_FOUND_REPORT = 5000
        private const val NOT_FOUND_SUSPEND_ACCOUNT = 5010
        private const val NOT_FOUND_REPORT_TARGET = 5020
        private const val DUPLICATED_REPORT = 5040
        private const val ALREADY_REVOKE_SUSPEND_ACCOUNT = 5070

        fun make(code:Int) : ReportError {
            return when(code) {
                NOT_FOUND_REPORT -> NotFoundReport("신 ID를 찾을 수 없음")
                NOT_FOUND_SUSPEND_ACCOUNT -> NotFoundSuspendAccount("정지된 계정을 찾을 수 없음")
                NOT_FOUND_REPORT_TARGET -> NotFoundReportTarget("신고 대상을 찾을 수 없음")
                DUPLICATED_REPORT -> DuplicatedReport("중복 신고")
                ALREADY_REVOKE_SUSPEND_ACCOUNT -> AlreadyRevokeSuspendAccount("이미 중지된 계정")
                else -> Common
            }
        }
    }

    data class NotFoundReport(val msg:String): ReportError()
    data class NotFoundSuspendAccount(val msg:String): ReportError()
    data class NotFoundReportTarget(val msg:String): ReportError()
    data class DuplicatedReport(val msg:String): ReportError()
    data class AlreadyRevokeSuspendAccount(val msg:String): ReportError()
    object Common : ReportError()
}
