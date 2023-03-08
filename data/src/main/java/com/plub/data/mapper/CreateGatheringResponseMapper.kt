package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.createGathering.CreateGatheringResponse
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo

object CreateGatheringResponseMapper: Mapper.ResponseMapper<CreateGatheringResponse, CreateGatheringResponseVo> {
    override fun mapDtoToModel(type: CreateGatheringResponse?): CreateGatheringResponseVo {
        return type?.run {
            CreateGatheringResponseVo(
                plubbingId = plubbingId
            )
        } ?: CreateGatheringResponseVo()
    }
}