package com.plub.domain.model.vo.home.recruitdetailvo

import com.plub.domain.base.DomainModel

data class RecruitDetailResponseVo(
    val recruitTitle : String,
    val recruitIntroduce : String,
    val categories : List<String>,
    val plubbingName : String,
    val plubbingGoal : String,
    //val plubbingMainImage : String,
    val plubbingDays : List<String>,
    val address : String,
    val roadAdress : String,
    val placeName : String,
    val remainAccountNum : Int,
    val plubbingTime : String,
    val isBookmarked : Boolean,
    val isApplied : Boolean,
    val curAccountNum : Int,
    val joinedAccounts : List<RecruitDetailJoinedAccountsListVo>
) : DomainModel()