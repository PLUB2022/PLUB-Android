package com.plub.presentation.ui.main.home.recruitment

import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.ui.PageState

data class RecruitmentPageState(
    val recruitTitle : String = "",
    val recruitIntroduce : String = "",
    val categories : List<String> = emptyList(),
    val plubbingName : String = "",
    val plubbingGoal : String = "",
    val plubbingMainImage : String = "",
    val plubbingDays : String = "",
    val placeName : String = "",
    val accountNum : String = "",
    val plubbingTime : String = "",
    val isBookmarked : Boolean = false,
    val isApplied : Boolean = false,
    val isHost : Boolean = false,
    val joinedAccounts : List<RecruitDetailJoinedAccountsVo> = emptyList()
) : PageState