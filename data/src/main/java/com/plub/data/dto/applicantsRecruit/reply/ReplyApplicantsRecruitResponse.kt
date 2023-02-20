package com.plub.data.dto.applicantsRecruit.reply

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ReplyApplicantsRecruitResponse(
    @SerializedName("maxAccountNum")
    val maxAccountNum : Int = -1,
    @SerializedName("curAccountNum")
    val curAccountNum : Int = -1
) : DataDto
