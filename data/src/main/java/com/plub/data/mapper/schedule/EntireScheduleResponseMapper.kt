package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CalendarListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object EntireScheduleResponseMapper :
    Mapper.ResponseMapper<GetEntireScheduleResponse, GetEntireScheduleResponseVo> {

    override fun mapDtoToModel(type: GetEntireScheduleResponse?): GetEntireScheduleResponseVo {
        return type?.run {
            GetEntireScheduleResponseVo(
                CalendarListVo(
                    totalPages = calendarList.totalPages,
                    totalElements = calendarList.totalElements,
                    last = calendarList.last,
                    content = this.calendarList.content.map {
                        ScheduleMapper.mapDtoToModel(it)
                    }
                )
            )
        } ?: GetEntireScheduleResponseVo()
    }
}