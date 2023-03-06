package com.plub.data.dto.applyrecruit

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class QuestionDataResponse(
    @SerializedName("id")
    val id : Int = -1,
    @SerializedName("question")
    val question: String = ""
) : DataDto