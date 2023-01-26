package com.plub.data.mapper.categoriesgatheringrequestmapper

import com.plub.data.dto.categoriesgathering.CategoriesGatheringRequest
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo

object CategoriesGatheringRequestMapper {
    fun mapDtoToModel(type: CategoriesGatheringRequestVo?): CategoriesGatheringRequest {
        return type?.run {
            CategoriesGatheringRequest(
                categoryId = this.categoryId,
                pageNumber = this.pageNumber
            )
        }  ?: CategoriesGatheringRequest(0, 0)
    }
}