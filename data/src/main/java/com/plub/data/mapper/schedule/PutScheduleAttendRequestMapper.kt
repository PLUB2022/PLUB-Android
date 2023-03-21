package com.plub.data.mapper.schedule

import com.plub.data.base.Mapper
import com.plub.data.dto.board.CommentCreateRequest
import com.plub.data.dto.schedule.PutScheduleAttendRequest
import com.plub.domain.model.vo.board.CommentCreateRequestVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo

object PutScheduleAttendRequestMapper: Mapper.RequestMapper<PutScheduleAttendRequest, PutScheduleAttendRequestVo> {

    override fun mapModelToDto(type: PutScheduleAttendRequestVo): PutScheduleAttendRequest {
        return type.run {
            PutScheduleAttendRequest(
                attendStatus = attendStatus
            )
        }
    }
}