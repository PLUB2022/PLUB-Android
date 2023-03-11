package com.plub.presentation.ui.main.gathering.createGathering

import com.plub.presentation.ui.Event

sealed class CreateGatheringEvent : Event {
    object NavigationPopEvent: CreateGatheringEvent()
    object GoToPrevPage: CreateGatheringEvent()
    data class GoToHostRecruitment(val plubbingId: Int): CreateGatheringEvent()

    object GoToHome: CreateGatheringEvent()
}