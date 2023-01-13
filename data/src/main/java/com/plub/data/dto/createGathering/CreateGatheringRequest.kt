package com.plub.data.dto.createGathering

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.enums.SocialLoginType

data class CreateGatheringRequest(
    @SerializedName("subCategoryIds")
    val subCategoryIds: List<Int>,
    @SerializedName("title")
    val title:String,
    @SerializedName("name")
    val name: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("introduce")
    val introduce: String,
    @SerializedName("mainImage")
    val mainImage: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("days")
    val days: List<String>,
    @SerializedName("onOff")
    val onOff: String,
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
    @SerializedName("maxAccountNum")
    val maxAccountNum: Int,
    @SerializedName("questions")
    val questions: List<String>
): DataDto