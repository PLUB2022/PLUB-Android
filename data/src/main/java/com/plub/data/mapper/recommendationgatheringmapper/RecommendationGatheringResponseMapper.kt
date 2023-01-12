package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.model.RecommendationGatheringResponse
import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringData
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

object RecommendationGatheringResponseMapper : Mapper.ResponseMapper<RecommendationGatheringResponse, RecommendationGatheringResponseVo>{
    override fun mapDtoToModel(type: RecommendationGatheringResponse?): RecommendationGatheringResponseVo {
        return type?.run {
            RecommendationGatheringResponseVo(
                RecommendationGatheringPlubbingsMapper.mapDtoToModel(plubbings)
            )
        }  ?: RecommendationGatheringResponseVo(RecommendationGatheringDataResponseVo(emptyList(), false,false,false,0,0,0,0,0))
    }
}