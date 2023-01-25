package com.plub.domain.model.vo.home.categorylistresponsevo

import com.plub.domain.base.DomainModel

data class CategoryListDataResponseVo(
    val categories : List<CategoriesDataResponseVo>
) : DomainModel()