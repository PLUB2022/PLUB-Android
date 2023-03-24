package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.schedule.CalendarAttendList
import com.plub.data.dto.schedule.CalendarAttendResponse
import com.plub.data.dto.schedule.Schedule
import com.plub.domain.model.enums.AttendStatus
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object CalendarAttendListMapper :
    Mapper.ResponseMapper<CalendarAttendList, CalendarAttendListVo> {

    override fun mapDtoToModel(type: CalendarAttendList?): CalendarAttendListVo {
        return type?.run {
            CalendarAttendListVo(
                calendarAttendList = calendarAttendResponseList.map {
                    CalendarAttendMapper.mapDtoToModel(it)
                }
            )
        } ?: CalendarAttendListVo()
    }
}