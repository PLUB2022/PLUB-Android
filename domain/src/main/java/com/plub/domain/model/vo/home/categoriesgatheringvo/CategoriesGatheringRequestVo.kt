package com.plub.domain.model.vo.home.categoriesgatheringvo

import com.plub.domain.base.DomainModel

data class CategoriesGatheringRequestVo(
    val categoryId : Int,
    val pageNumber : Int,
    val accessToken : String
) : DomainModel()
