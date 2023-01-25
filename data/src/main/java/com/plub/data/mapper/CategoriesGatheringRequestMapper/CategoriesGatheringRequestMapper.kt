package com.plub.data.mapper.CategoriesGatheringRequestMapper

import com.plub.data.model.CategoriesGatheringRequest
import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringRequest
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo

object CategoriesGatheringRequestMapper {
    fun mapDtoToModel(type: CategoriesGatheringRequestVo?): CategoriesGatheringRequest {
        return type?.run {
            CategoriesGatheringRequest(
                categoryId, pageNumber, accessToken)
        }  ?: CategoriesGatheringRequest(0, 0, "")
    }
}