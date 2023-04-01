package com.plub.data.dto.myPage

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class MyGatheringDataResponse(
    @SerializedName("goal")
    val goal : String = "",

    @SerializedName("iconImage")
    val iconImage : String = "",

    @SerializedName("myPlubbingStatus")
    val myPlubbingStatus : String ="",

    @SerializedName("plubbingId")
    val plubbingId : Int = -1,

    @SerializedName("title")
    val title : String = ""
) : DataDto
