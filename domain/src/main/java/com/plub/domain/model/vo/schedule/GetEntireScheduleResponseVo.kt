package com.plub.domain.model.vo.schedule

import com.plub.domain.model.DomainModel

data class GetEntireScheduleResponseVo(
    val totalPages: Int = -1,
    val totalElements: Int = -1,
    val content: List<ScheduleVo> = emptyList(),
    val last: Boolean = false,
) : DomainModel

data class ScheduleVo(
    val calendarId: Int =  -1,
    val title: String = "",
    val memo: String = "",
    val startedAt: String = "",
    val endedAt: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val isAllDay: Boolean = false,
    val address: String = "",
    val roadAddress: String = "",
    val placeName: String = "",
    val alarmType: String = "",
    val calendarAttendList: CalendarAttendListVo = CalendarAttendListVo()
)

data class CalendarAttendListVo(
    val calendarAttendList: List<CalendarAttendVo> = emptyList()
)

data class CalendarAttendVo(
    val calendarAttendId: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val AttendStatus: String = ""
)