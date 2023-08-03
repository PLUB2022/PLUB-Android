package com.plub.data.dto.modifyGathering

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.enums.SocialLoginType

data class ModifyInfoRequest(
    val plubbingId: Int,
    val body: ModifyInfoRequestBody
): DataDto

data class ModifyInfoRequestBody(
    @SerializedName("days")
    val days:List<String>,
    @SerializedName("onOff")
    val onOff: String,
    @SerializedName("maxAccountNum")
    val maxAccountNum: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("roadAddress")
    val roadAddress: String,
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("placePositionX")
    val placePositionX: Float,
    @SerializedName("placePositionY")
    val placePositionY: Float,
    @SerializedName("time")
    val time: String,
): DataDto