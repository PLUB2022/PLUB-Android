package com.plub.data.dto.plub

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingMainResponse(
    @SerializedName("plubbingId")
    val plubbingId: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("goal")
    val goal: String = "",
    @SerializedName("time")
    val time: String = "",
    @SerializedName("days")
    val days: List<String> = emptyList(),
    @SerializedName("mainImage")
    val mainImage: String = "",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("roadAddress")
    val roadAddress: String = "",
    @SerializedName("placeName")
    val placeName: String = "",
    @SerializedName("placePositionX")
    val placePositionX: Float = 0f,
    @SerializedName("placePositionY")
    val placePositionY: Float = 0f,
    @SerializedName("accountInfo")
    val accountInfo: List<PlubingAccountInfoResponse> = emptyList(),
) : DataDto