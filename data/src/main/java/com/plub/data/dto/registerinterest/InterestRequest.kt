package com.plub.data.dto.registerinterest

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class InterestRequest(
    @SerializedName("categoryId")
    val categoryId : List<Int>
) :DataDto