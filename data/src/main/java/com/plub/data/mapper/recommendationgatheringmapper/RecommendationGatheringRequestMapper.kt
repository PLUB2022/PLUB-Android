package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringRequest
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo

object RecommendationGatheringRequestMapper {
    fun mapDtoToModel(type: RecommendationGatheringRequestVo?): RecommendationGatheringRequest {
        return type?.run {
            RecommendationGatheringRequest(
                pageNum)
        }  ?: RecommendationGatheringRequest(0)
    }
}