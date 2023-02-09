package com.plub.presentation.ui.main.home.recruitment

import com.plub.presentation.ui.Event

sealed class RecruitEvent: Event {
    object GoToApplyPlubbingFragment : RecruitEvent()
    object GoToBack : RecruitEvent()
    data class GoToProfileFragment(val accountId : Int) : RecruitEvent()
}
