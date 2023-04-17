package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.board.CommentCreateRequest
import com.plub.data.dto.schedule.CreateScheduleRequest
import com.plub.data.dto.schedule.PutScheduleAttendRequest
import com.plub.domain.model.vo.board.CommentCreateRequestVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.EditScheduleRequestVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo

object EditScheduleRequestMapper: Mapper.RequestMapper<CreateScheduleRequest, EditScheduleRequestVo> {

    override fun mapModelToDto(type: EditScheduleRequestVo): CreateScheduleRequest {
        return type.run {
            CreateScheduleRequest(
                title = title,
                memo = memo,
                startedAt = startedAt,
                endedAt = endedAt,
                startTime = startTime,
                endTime = endTime,
                isAllDay = isAllDay,
                address = address,
                roadAddress = roadAddress,
                placeName = placeName,
                alarmType = alarmType
            )
        }
    }
}