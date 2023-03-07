package com.plub.data.dto.recruitdetail

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.recruitdetail.RecruitDetailJoinedAccountsDataResponse

data class RecruitDetailResponse(
    @SerializedName("title")
    val title : String = "",
    @SerializedName("introduce")
    val introduce : String = "",
    @SerializedName("categories")
    val categories : List<String> = emptyList(),
    @SerializedName("name")
    val name : String ="",
    @SerializedName("goal")
    val goal : String = "",
    @SerializedName("mainImage")
    val mainImage : String = "",
    @SerializedName("days")
    val days : List<String> = emptyList(),
    @SerializedName("time")
    val time : String = "",
    @SerializedName("address")
    val address : String = "",
    @SerializedName("roadAddress")
    val roadAddress : String = "",
    @SerializedName("placeName")
    val placeName : String = "",
    @SerializedName("placePositionX")
    val placePositionX : Double =0.0,
    @SerializedName("placePositionY")
    val placePositionY : Double = 0.0,
    @SerializedName("isBookmarked")
    val isBookmarked : Boolean =false,
    @SerializedName("isApplied")
    val isApplied : Boolean = false,
    @SerializedName("curAccountNum")
    val curAccountNum : Int = -1,
    @SerializedName("remainAccountNum")
    val remainAccountNum : Int = -1,
    @SerializedName("joinedAccounts")
    val joinedAccounts : List<RecruitDetailJoinedAccountsDataResponse> = emptyList()
):DataDto