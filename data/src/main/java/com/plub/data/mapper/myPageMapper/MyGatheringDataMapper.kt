package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyGatheringDataResponse
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo

object MyGatheringDataMapper: Mapper.ResponseMapper<MyGatheringDataResponse, MyPageGatheringDetailVo> {
    override fun mapDtoToModel(type: MyGatheringDataResponse?): MyPageGatheringDetailVo {
        return type?.run {
            MyPageGatheringDetailVo(
                image = iconImage,
                title = title,
                goal =goal,
                gatheringType = myPlubbingStatus,
            )
        }?: MyPageGatheringDetailVo()
    }
}