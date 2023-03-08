package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PlubingBoardResponse
import com.plub.data.dto.schedule.CalendarList
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CalendarListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import timber.log.Timber

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
                        ScheduleVo(
                            calendarId = it.calendarId,
                            title = it.title,
                            memo = it.memo,
                            startedAt = it.startedAt,
                            endedAt = it.endedAt,
                            startTime = it.startTime,
                            endTime = it.endTime,
                            isAllDay = it.isAllDay,
                            address = it.address,
                            roadAddress = it.roadAddress,
                            placeName = it.placeName,
                            alarmType = it.alarmType,
                            calendarAttendList = CalendarAttendListVo(it.calendarAttendList.calendarAttendList.map { calendar ->
                                CalendarAttendVo(
                                    calendarAttendId = calendar.calendarAttendId,
                                    nickname = calendar.nickname,
                                    profileImage = calendar.profileImage,
                                    AttendStatus = calendar.AttendStatus
                                )
                            })
                        )
                    }
                )
            )
        } ?: GetEntireScheduleResponseVo()
    }
}