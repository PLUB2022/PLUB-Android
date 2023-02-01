package com.plub.domain.model.vo.home.categorylistresponsevo

import com.plub.domain.model.DomainModel

data class CategoryListDataResponseVo(
    val categories : List<CategoriesDataResponseVo> = emptyList()
) : DomainModel