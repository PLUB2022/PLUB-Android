package com.plub.presentation.ui.main.home.recruitment.hostrecruitment

import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.ui.Event

sealed class HostDetailPageEvent : Event {
    object GoToBack : HostDetailPageEvent()
    object GoToSeeApplicants : HostDetailPageEvent()
    object GoToEditFragment : HostDetailPageEvent()
    data class GoToProfile(val accountId : Int) : HostDetailPageEvent()
    data class OpenBottomSheet(val joinedAccountsList : List<RecruitDetailJoinedAccountsListVo>) : HostDetailPageEvent()
}
