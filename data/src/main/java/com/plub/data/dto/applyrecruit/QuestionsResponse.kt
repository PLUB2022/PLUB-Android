package com.plub.data.dto.applyrecruit

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class QuestionsResponse(
    @SerializedName("questions")
    val questions : List<Questions> = emptyList()
) : DataDto