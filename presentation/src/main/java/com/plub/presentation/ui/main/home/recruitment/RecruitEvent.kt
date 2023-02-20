package com.plub.presentation.ui.main.home.recruitment

import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.ui.Event

sealed class RecruitEvent: Event {
    object GoToApplyPlubbingFragment : RecruitEvent()
    object GoToBack : RecruitEvent()
    data class OpenBottomSheet(val joinedAccountsList : List<RecruitDetailJoinedAccountsListVo>) : RecruitEvent()
    data class GoToProfileFragment(val accountId : Int) : RecruitEvent()
}
