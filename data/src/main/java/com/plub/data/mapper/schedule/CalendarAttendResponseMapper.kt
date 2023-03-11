package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.schedule.CalendarAttendResponse
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CalendarListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object CalendarAttendResponseMapper :
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