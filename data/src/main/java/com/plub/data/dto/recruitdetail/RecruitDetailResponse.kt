package com.plub.data.dto.recruitdetail

import com.plub.data.base.DataDto

data class RecruitDetailResponse(
    val recruitTitle : String,
    val recruitIntroduce : String,
    val categories : List<String>,
    val plubbingName : String,
    val plubbingGoal : String,
    val plubbingMainImage : String,
    val plubbingDays : List<String>,
    val plubbingTime : String,
    val isBookmarked : Boolean,
    val isApplied : Boolean,
    val curAccountNum : Int,
    val joinedAccounts : List<RecruitDetailJoinedAccountsList>
):DataDto