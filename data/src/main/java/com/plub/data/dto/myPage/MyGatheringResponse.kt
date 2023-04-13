package com.plub.data.dto.myPage

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class MyGatheringResponse(
    @SerializedName("plubbingStatus")
    val plubbingStatus : String = "",

    @SerializedName("plubbings")
    val plubbings : List<MyGatheringDataResponse> = emptyList()
) : DataDto
