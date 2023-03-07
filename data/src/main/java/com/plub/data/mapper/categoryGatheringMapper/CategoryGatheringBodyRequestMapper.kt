package com.plub.data.mapper.categoryGatheringMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plub.CategoryGatheringBodyRequest
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringBodyRequestVo

object CategoryGatheringBodyRequestMapper: Mapper.RequestMapper<CategoryGatheringBodyRequest, CategoriesGatheringBodyRequestVo> {
    override fun mapModelToDto(type: CategoriesGatheringBodyRequestVo): CategoryGatheringBodyRequest {
        return type.run {
            CategoryGatheringBodyRequest(
                days = days,
                subCategoryId = subCategoryId,
                accountNum = accountNum
            )
        }
    }
}