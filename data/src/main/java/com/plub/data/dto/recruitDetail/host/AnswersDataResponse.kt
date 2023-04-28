package com.plub.data.dto.recruitDetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class AnswersDataResponse(
    @SerializedName("questionId")
    val questionId : Int = 0,
    @SerializedName("question")
    val question : String = "",
    @SerializedName("answer")
    val answer : String = ""
) : DataDto