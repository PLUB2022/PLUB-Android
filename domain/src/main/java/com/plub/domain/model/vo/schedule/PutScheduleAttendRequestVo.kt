package com.plub.domain.model.vo.schedule

import com.plub.domain.model.enums.AttendStatus

data class PutScheduleAttendRequestVo(
    val plubbingId: Int = -1,
    val calendarId: Int = -1,
    val attendStatus: String = AttendStatus.NO.value
)
