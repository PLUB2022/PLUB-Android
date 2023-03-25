package com.plub.data.mapper.myGathering

import com.plub.data.base.Mapper
import com.plub.data.dto.myGathering.MyGatheringResponse
import com.plub.data.dto.myGathering.MyGatheringsResponse
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.data.dto.schedule.Schedule
import com.plub.data.util.DateFormatUtil
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.domain.model.vo.myGathering.MyGatheringsResponseVo
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CalendarListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo

object MyGatheringsMapper :
    Mapper.ResponseMapper<MyGatheringsResponse, MyGatheringsResponseVo> {

    override fun mapDtoToModel(type: MyGatheringsResponse?): MyGatheringsResponseVo {
        return type?.run {
            MyGatheringsResponseVo(
                plubbings = plubbings.map { MyGatheringMapper.mapDtoToModel(it) }
            )
        } ?: MyGatheringsResponseVo()
    }
}