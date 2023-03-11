package com.plub.domain.model.vo.schedule

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.ScheduleCardType
import java.io.Serializable

data class GetEntireScheduleResponseVo(
    val calendarList: CalendarListVo = CalendarListVo()
) : DomainModel

data class CalendarListVo(
    val totalPages: Int = -1,
    val totalElements: Int = -1,
    val content: List<ScheduleVo> = emptyList(),
    val last: Boolean = false,
)

data class ScheduleVo(
    val viewType: ScheduleCardType = ScheduleCardType.CONTENT,
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
): Serializable

data class CalendarAttendListVo(
    val calendarAttendList: List<CalendarAttendVo> = emptyList()
)

data class CalendarAttendVo(
    val calendarAttendId: Int = -1,
    val nickname: String = "",
    val profileImage: String = "",
    val AttendStatus: String = ""
)