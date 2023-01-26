package com.plub.data.dto.categorylistdata

import com.plub.data.base.DataDto

data class CategoryListDataResponse(
    val categories : List<CategoriesData> = emptyList()
) : DataDto