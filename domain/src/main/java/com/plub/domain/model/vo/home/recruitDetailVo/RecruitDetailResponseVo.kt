package com.plub.domain.model.vo.home.recruitDetailVo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo

data class RecruitDetailResponseVo(
    val recruitTitle : String = "",
    val recruitIntroduce : String = "",
    val categories : List<String> = emptyList(),
    val plubbingName : String = "",
    val plubbingGoal : String = "",
    val plubbingMainImage : String = "",
    val plubbingDays : List<String> = emptyList(),
    val address : String = "",
    val roadAdress : String = "",
    val placeName : String = "",
    val remainAccountNum : Int = -1,
    val plubbingTime : String = "",
    val isBookmarked : Boolean = false,
    val isApplied : Boolean = false,
    val curAccountNum : Int = -1,
    val joinedAccounts : List<RecruitDetailJoinedAccountsVo> = emptyList()
) : DomainModel