package com.plub.data.dto.plub

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubInfoResponse(
    @SerializedName("plubbingId")
    val plubbingId : Int = -1,
    @SerializedName("name")
    val name : String ="",
    @SerializedName("days")
    val days : List<String> = emptyList(),
    @SerializedName("address")
    val address : String = "",
    @SerializedName("roadAddress")
    val roadAddress : String = "",
    @SerializedName("placeName")
    val placeName : String = "",
    @SerializedName("goal")
    val goal : String = "",
    @SerializedName("time")
    val time : String =""
) : DataDto