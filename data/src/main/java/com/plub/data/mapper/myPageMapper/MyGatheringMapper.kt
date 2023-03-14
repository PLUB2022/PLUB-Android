package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyGatheringResponse
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo

object MyGatheringMapper : Mapper.ResponseMapper<MyGatheringResponse, MyPageGatheringVo> {
    override fun mapDtoToModel(type: MyGatheringResponse?): MyPageGatheringVo {
        return type?.run {
            MyPageGatheringVo(
                gatheringList = plubbings.map {
                    MyGatheringDataMapper.mapDtoToModel(it)
                },
                gatheringType = MyPageGatheringStateType.valuesOf(plubbingStatus)
            )
        }?: MyPageGatheringVo()
    }
}