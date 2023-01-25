package com.plub.domain.model.vo.home.categoriesgatheringresponse

import com.plub.domain.base.DomainModel

data class CategoriesGatheringRequestVo(
    val categoryId : Int,
    val pageNumber : Int
) : DomainModel()