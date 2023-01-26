package com.plub.data.dto.recruitdetail

import com.plub.data.base.DataDto

data class RecruitDetailResponse(

    val title : String = "",
    val introduce : String = "",
    val categories : List<String> = emptyList(),
    val name : String ="",
    val goal : String = "",
    //val mainImage : String = "",
    val days : List<String> = emptyList(),
    val time : String = "",
    val address : String = "",
    val roadAddress : String = "",
    val placeName : String = "",
    val placePositionX : Double =0.0,
    val placePositionY : Double = 0.0,
    val isBookmarked : Boolean =false,
    val isApplied : Boolean = false,
    val curAccountNum : Int = -1,
    val remainAccountNum : Int = -1,
    val joinedAccounts : List<RecruitDetailJoinedAccountsList> = emptyList()
):DataDto