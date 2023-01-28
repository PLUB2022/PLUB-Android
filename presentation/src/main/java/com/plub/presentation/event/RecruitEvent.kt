package com.plub.presentation.event

sealed class RecruitEvent:Event{
    object GoToApplyPlubbingFragment : RecruitEvent()
    object GoToProfileFragment : RecruitEvent()
}
