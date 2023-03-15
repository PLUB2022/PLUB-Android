package com.plub.data.dto.myPage

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.plub.PlubInfoResponse
import com.plub.data.dto.recruitdetail.host.AnswersDataResponse

data class MyApplicationResponse(
    @SerializedName("recruitDate")
    val recruitDate : String = "",
    @SerializedName("plubbingInfo")
    val plubbingInfo : PlubInfoResponse = PlubInfoResponse(),
    @SerializedName("answers")
    val answers : List<AnswersDataResponse>
) : DataDto