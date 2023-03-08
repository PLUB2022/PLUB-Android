package com.plub.domain.model.vo.home.categoriesGatheringVo

import com.plub.domain.model.DomainModel

data class CategoriesGatheringParamsVo(
    val categoryId : Int,
    val sort : String,
    val pageNumber : Int
) : DomainModel
