package com.plub.data.mapper.myGathering

import com.plub.data.base.Mapper
import com.plub.data.dto.myGathering.MyGatheringResponse
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.data.dto.schedule.Schedule
import com.plub.data.util.DateFormatUtil
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CalendarListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object MyGatheringMapper :
    Mapper.ResponseMapper<MyGatheringResponse, MyGatheringResponseVo> {

    override fun mapDtoToModel(type: MyGatheringResponse?): MyGatheringResponseVo {
        return type?.run {
            MyGatheringResponseVo(
                plubbingId = plubbingId,
                name = name,
                goal = goal,
                mainImage = mainImage,
                time = time,
                days = days
            )
        } ?: MyGatheringResponseVo()
    }
}