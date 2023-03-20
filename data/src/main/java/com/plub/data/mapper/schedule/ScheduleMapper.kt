package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.data.dto.schedule.Schedule
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CalendarListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object ScheduleMapper :
    Mapper.ResponseMapper<Schedule, ScheduleVo> {

    override fun mapDtoToModel(type: Schedule?): ScheduleVo {
        return type?.run {
            ScheduleVo(
                calendarId = calendarId,
                title = title,
                memo = memo,
                startedAt = startedAt,
                endedAt = endedAt,
                startTime = startTime,
                endTime = endTime,
                isAllDay = isAllDay,
                isEditable = isEditable,
                address = address,
                roadAddress = roadAddress,
                placeName = placeName,
                alarmType = alarmType,
                calendarAttendList = CalendarAttendListMapper.mapDtoToModel(calendarAttendList)
            )
        } ?: ScheduleVo()
    }
}