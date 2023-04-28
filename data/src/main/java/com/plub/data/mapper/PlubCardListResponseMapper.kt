package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plub.PlubCardListResponse
import com.plub.domain.model.vo.plub.PlubCardListVo

object PlubCardListResponseMapper : Mapper.ResponseMapper<PlubCardListResponse, PlubCardListVo> {

    override fun mapDtoToModel(type: PlubCardListResponse?): PlubCardListVo {
        return type?.run {
            PlubCardListVo(
                totalElements = totalElements,
                content = content.map {
                    PlubCardResponseMapper.mapDtoToModel(it)
                },
                last = last
            )
        }?: PlubCardListVo()
    }
}