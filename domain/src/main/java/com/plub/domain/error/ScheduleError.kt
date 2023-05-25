package com.plub.domain.error

sealed class ScheduleError: IndividualError() {

    companion object{
        private const val NOT_FOUND_CALENDAR = 7500
        private const val NOT_FOUND_CALENDAR_ATTEND = 7510
        private const val NOT_AUTHORITY = 7520

        fun make(code:Int) : ScheduleError {
            return when(code) {
                NOT_FOUND_CALENDAR -> NotFoundCalendar("일정 id를 찾을 수 없음")
                NOT_FOUND_CALENDAR_ATTEND -> NotFoundCalendarAttend("참여 여부를 찾을 수 없")
                NOT_AUTHORITY -> NotAuthority("권한이 없음")
                else -> Common
            }
        }
    }

    data class NotFoundCalendar(val msg:String): ScheduleError()
    data class NotFoundCalendarAttend(val msg:String): ScheduleError()
    data class NotAuthority(val msg:String): ScheduleError()
    object Common : ScheduleError()
}
