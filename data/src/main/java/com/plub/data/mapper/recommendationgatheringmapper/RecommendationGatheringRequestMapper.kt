package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringData
import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringRequest
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

object RecommendationGatheringRequestMapper {
    fun mapDtoToModel(type: RecommendationGatheringRequestVo?): RecommendationGatheringRequest {
        return type?.run {
            RecommendationGatheringRequest(
                pageNum, accessToken)
        }  ?: RecommendationGatheringRequest(0, "")
    }
}