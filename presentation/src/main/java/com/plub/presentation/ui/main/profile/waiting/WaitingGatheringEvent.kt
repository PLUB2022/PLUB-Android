package com.plub.presentation.ui.main.profile.waiting

import com.plub.presentation.ui.Event

sealed class WaitingGatheringEvent : Event {
    object GoToBack : WaitingGatheringEvent()
    object GoToModifyApplication : WaitingGatheringEvent()
    object ShowCancelDialog : WaitingGatheringEvent()
}
