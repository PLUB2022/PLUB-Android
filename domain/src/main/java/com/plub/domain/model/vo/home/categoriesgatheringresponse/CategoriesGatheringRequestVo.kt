package com.plub.domain.model.vo.home.categoriesgatheringresponse

import com.plub.domain.model.DomainModel

data class CategoriesGatheringRequestVo(
    val categoryId : Int,
    val sort : String,
    val pageNumber : Int
) : DomainModel