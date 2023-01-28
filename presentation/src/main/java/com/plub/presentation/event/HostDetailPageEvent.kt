package com.plub.presentation.event

sealed class HostDetailPageEvent : Event{
    object GoToBack : HostDetailPageEvent()
    object GoToSeeApplicants : HostDetailPageEvent()
    object GoToEditFragment : HostDetailPageEvent()
}
