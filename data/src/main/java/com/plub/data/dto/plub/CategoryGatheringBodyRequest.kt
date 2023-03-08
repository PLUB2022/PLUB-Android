package com.plub.data.dto.plub

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CategoryGatheringBodyRequest(
    @SerializedName("days")
    val days : List<String>?,
    @SerializedName("subCategoryId")
    val subCategoryId : List<Int>?,
    @SerializedName("accountNum")
    val accountNum : Int?,
) : DataDto
