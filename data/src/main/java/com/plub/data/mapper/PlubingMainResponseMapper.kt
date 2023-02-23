package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plub.PlubingMainResponse
import com.plub.domain.model.vo.plub.PlubingMainVo

object PlubingMainResponseMapper : Mapper.ResponseMapper<PlubingMainResponse, PlubingMainVo> {
    override fun mapDtoToModel(type: PlubingMainResponse?): PlubingMainVo {
        return type?.run {
            PlubingMainVo(
                plubingId = plubbingId,
                name = name,
                mainImage = mainImage,
                days = days,
                time = time,
                address = address,
                roadAddress = roadAddress,
                placeName = placeName,
                memberInfoList = accountInfo.map {
                    PlubingAccountInfoResponseMapper.mapDtoToModel(it)
                }
            )
        } ?: PlubingMainVo()
    }
}