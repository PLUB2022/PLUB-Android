package com.plub.data.dto.applicantsRecruit

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ApplicantsRecruitRequestAnswerListRequest(
    @SerializedName("questionId")
    val questionId : Int = 0,
    @SerializedName("answer")
    val answer : String = ""
) : DataDto