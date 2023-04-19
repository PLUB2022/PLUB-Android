package com.plub.presentation.ui.main.home.recruitment

import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class RecruitmentPageState(
    val recruitTitle : StateFlow<String>,
    val recruitIntroduce : StateFlow<String>,
    val categories : StateFlow<List<String>>,
    val plubbingName : StateFlow<String>,
    val plubbingGoal : StateFlow<String>,
    val plubbingMainImage : StateFlow<String>,
    val plubbingDays : StateFlow<String>,
    val placeName : StateFlow<String>,
    val accountNum : StateFlow<String>,
    val plubbingTime : StateFlow<String>,
    val isBookmarked : StateFlow<Boolean>,
    val isApplied : StateFlow<Boolean>,
    val isHost : StateFlow<Boolean>,
    val joinedAccounts : StateFlow<List<RecruitDetailJoinedAccountsVo>>
) : PageState