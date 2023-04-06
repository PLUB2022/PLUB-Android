package com.plub.presentation.ui.main.home.recruitment

import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.ui.Event

sealed class RecruitEvent: Event {
    object GoToApplyPlubbingFragment : RecruitEvent()
    object GoToBack : RecruitEvent()
    object GoToReport : RecruitEvent()
    data class OpenBottomSheet(val joinedAccountsList : List<RecruitDetailJoinedAccountsVo>) : RecruitEvent()
    data class GoToProfileFragment(val accountId : Int, val nickname : String) : RecruitEvent()
}
