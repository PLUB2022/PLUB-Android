package com.plub.presentation.ui.main.home.profile.waiting

import com.plub.presentation.ui.Event

sealed class WaitingGatheringEvent : Event {
    object GoToBack : WaitingGatheringEvent()
}
