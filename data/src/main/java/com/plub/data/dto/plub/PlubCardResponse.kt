package com.plub.data.dto.plub

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubCardResponse(
    @SerializedName("plubbingId")
    val plubbingId: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("introduce")
    val introduce: String = "",
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
    @SerializedName("remainAccountNum")
    val remainAccountNum: Int = -1,
    @SerializedName("time")
    val time: String = "",
    @SerializedName("curAccountNum")
    val curAccountNum: Int = -1,
    @SerializedName("isBookmarked")
    val isBookmarked: Boolean = false,
    @SerializedName("isHost")
    val isHost : Boolean = false
) : DataDto