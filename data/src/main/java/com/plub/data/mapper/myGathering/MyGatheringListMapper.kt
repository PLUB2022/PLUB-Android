package com.plub.data.mapper.myGathering

import com.plub.data.base.Mapper
import com.plub.data.dto.myGathering.MyGatheringListResponse
import com.plub.domain.model.vo.myGathering.MyGatheringListResponseVo

object MyGatheringListMapper :
    Mapper.ResponseMapper<MyGatheringListResponse, MyGatheringListResponseVo> {

    override fun mapDtoToModel(type: MyGatheringListResponse?): MyGatheringListResponseVo {
        return type?.run {
            MyGatheringListResponseVo(
                plubbings = plubbings.map { MyGatheringMapper.mapDtoToModel(it) }
            )
        } ?: MyGatheringListResponseVo()
    }
}