package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringResponse
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

object RecommendationGatheringResponseMapper : Mapper.ResponseMapper<RecommendationGatheringResponse, RecommendationGatheringResponseVo>{
    override fun mapDtoToModel(type: RecommendationGatheringResponse?): RecommendationGatheringResponseVo {
        return type?.run {
            RecommendationGatheringResponseVo(
                content = this.content.map {
                    RecommendationGatheringContentMapper.mapDtoToModel(it)
                },
                last = this.last,
                number = this.totalPages,
                numberOfElements = this.totalElements
            )
        }  ?: RecommendationGatheringResponseVo(emptyList(),false,0,0)
    }
}