package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plub.PlubCardResponse
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.plub.PlubCardVo

object PlubCardResponseMapper : Mapper.ResponseMapper<PlubCardResponse, PlubCardVo> {

    override fun mapDtoToModel(type: PlubCardResponse?): PlubCardVo {
        return type?.run {
            PlubCardVo(
                id = plubbingId,
                viewType = PlubCardType.LIST,
                photo = mainImage,
                name = name,
                title = title,
                time = time,
                days = days,
                place = address,
                remainMemberNumber = remainAccountNum,
                isBookmarked = isBookmarked,
                isHost = isHost
            )
        }?: PlubCardVo()
    }
}