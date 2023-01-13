package com.plub.data.mapper.categoriesgatheringrequestmapper

import com.plub.data.dto.plubJwt.categoriesgathering.CategoriesGatheringRequest
import com.plub.domain.model.vo.home.categoriesgatheringvo.CategoriesGatheringRequestVo

object CategoriesGatheringRequestMapper {
    fun mapDtoToModel(type: CategoriesGatheringRequestVo?): CategoriesGatheringRequest {
        return type?.run {
            CategoriesGatheringRequest(
                categoryId, pageNumber, accessToken)
        }  ?: CategoriesGatheringRequest(0, 0, "")
    }
}