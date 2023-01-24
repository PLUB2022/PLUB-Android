package com.plub.presentation.ui.createGathering

import com.plub.presentation.event.Event
import com.plub.presentation.state.PageState

sealed class CreateGatheringEvent : Event {
    object NavigationPopEvent: CreateGatheringEvent()
    object GoToPrevPage: CreateGatheringEvent()
}