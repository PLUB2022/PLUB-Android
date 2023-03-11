package com.plub.presentation.ui.main.home.recruitment.hostrecruitment

import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.ui.Event

sealed class HostDetailPageEvent : Event {
    object GoToBack : HostDetailPageEvent()
    data class GoToSeeApplicants(val plubbingId : Int) : HostDetailPageEvent()
    data class GoToEditFragment(val plubbingId: Int) : HostDetailPageEvent()
    data class GoToProfile(val accountId : Int) : HostDetailPageEvent()
    data class OpenBottomSheet(val joinedAccountsList : List<RecruitDetailJoinedAccountsVo>) : HostDetailPageEvent()
}
