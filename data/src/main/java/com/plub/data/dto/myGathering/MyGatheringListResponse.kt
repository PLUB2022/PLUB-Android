package com.plub.data.dto.myGathering

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class MyGatheringListResponse(
    @SerializedName("plubbings")
    val plubbings: List<MyGatheringResponse> = emptyList()
) : DataDto

data class MyGatheringResponse(
    @SerializedName("plubbingId")
    val plubbingId: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("goal")
    val goal: String = "",
    @SerializedName("mainImage")
    val mainImage: String = "",
    @SerializedName("time")
    val time: String = "00:00",
    @SerializedName("days")
    val days: List<String> = emptyList()
) : DataDto
