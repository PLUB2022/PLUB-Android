package com.plub.data.dto.categorylistdata

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CategoryListDataResponse(
    @SerializedName("categories")
    val categories : List<CategoriesData> = emptyList()
) : DataDto