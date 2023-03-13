package com.plub.data.dto.categoryListData

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CategoryListResponse(
    @SerializedName("categories")
    val categories : List<CategoriesDataResponse> = emptyList()
) : DataDto