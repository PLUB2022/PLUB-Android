package com.plub.presentation.event

import com.plub.presentation.ui.Event

sealed class RecruitEvent: Event {
    object GoToApplyPlubbingFragment : RecruitEvent()
    object GoToProfileFragment : RecruitEvent()
    object GoToBack :RecruitEvent()
}
