package com.plub.data.dto.myPage

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class MyApplicationDeleteResponse(
    @SerializedName("plubbingId")
    val plubbingId : Int = -1,

    @SerializedName("accountId")
    val accountId : Int = -1
) : DataDto
