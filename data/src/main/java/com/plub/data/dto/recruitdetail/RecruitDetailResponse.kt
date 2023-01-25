package com.plub.data.dto.recruitdetail

import com.plub.data.base.DataDto

data class RecruitDetailResponse(

    val title : String,
    val introduce : String,
    val categories : List<String>,
    val name : String,
    val goal : String,
    //val mainImage : String,
    val days : List<String>,
    val time : String,
    val address : String,
    val roadAddress : String,
    val placeName : String,
    val placePositionX : Double,
    val placePositionY : Double,
    val isBookmarked : Boolean,
    val isApplied : Boolean,
    val curAccountNum : Int,
    val remainAccountNum : Int,
    val joinedAccounts : List<RecruitDetailJoinedAccountsList>
):DataDto