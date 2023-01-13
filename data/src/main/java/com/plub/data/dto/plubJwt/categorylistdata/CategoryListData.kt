package com.plub.data.dto.plubJwt.categorylistdata

import com.plub.data.base.DataDto

data class CategoryListData(
    val categories : List<CategoriesData>
) : DataDto
