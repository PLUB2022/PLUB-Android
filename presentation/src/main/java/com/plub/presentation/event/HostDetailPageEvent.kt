package com.plub.presentation.event

import com.plub.presentation.ui.Event

sealed class HostDetailPageEvent : Event {
    object GoToBack : HostDetailPageEvent()
    object GoToSeeApplicants : HostDetailPageEvent()
    object GoToEditFragment : HostDetailPageEvent()
}
