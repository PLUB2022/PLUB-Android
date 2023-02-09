package com.plub.domain.model.vo.home.categorylistresponsevo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.HomeCategoryViewType

data class CategoryListDataResponseVo(
    val viewType: HomeCategoryViewType = HomeCategoryViewType.CATEGORY_VIEW,
    val categories : List<CategoriesDataResponseVo> = emptyList()
) : DomainModel