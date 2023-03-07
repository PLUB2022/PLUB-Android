package com.plub.domain.model.vo.home.categoriesGatheringVo

import com.plub.domain.model.DomainModel

data class CategoriesGatheringBodyRequestVo(
    val days : List<String>? = null,
    val subCategoryId : List<Int>? = null,
    val accountNum : Int? = null,
) : DomainModel
