package com.plub.data.dto.applicantsRecruit

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ApplicantsRecruitResponse(
    @SerializedName("plubbingId")
    val plubbingId : Int = -1
) : DataDto