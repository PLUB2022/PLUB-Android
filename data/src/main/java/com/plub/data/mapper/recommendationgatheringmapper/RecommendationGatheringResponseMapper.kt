package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recommendationgatheringdata.RecommendationGatheringResponse
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

object RecommendationGatheringResponseMapper : Mapper.ResponseMapper<RecommendationGatheringResponse, RecommendationGatheringResponseVo>{
    override fun mapDtoToModel(type: RecommendationGatheringResponse?): RecommendationGatheringResponseVo {
        return type?.run {
            RecommendationGatheringResponseVo(
                content.map {
                    RecommendationGatheringContentMapper.mapDtoToModel(it)
                },
                last,
                totalPages,
                totalElements
            )
        }  ?: RecommendationGatheringResponseVo(emptyList(),false,0,0)
    }
}