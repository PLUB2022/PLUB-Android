package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.schedule.CalendarAttendResponse
import com.plub.data.dto.schedule.Schedule
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object CalendarAttendMapper :
    Mapper.ResponseMapper<CalendarAttendResponse, CalendarAttendVo> {

    override fun mapDtoToModel(type: CalendarAttendResponse?): CalendarAttendVo {
        return type?.run {
            CalendarAttendVo(
                calendarAttendId = calendarAttendId,
                nickname = nickname,
                profileImage = profileImage,
                AttendStatus = AttendStatus
            )
        } ?: CalendarAttendVo()
    }
}