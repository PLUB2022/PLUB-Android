package com.plub.data.dto.categorylistdata

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CategoriesDataResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("icon")
    val icon : String
): DataDto
