package com.plub.presentation.ui.main.gathering.createGathering

import com.plub.presentation.ui.Event

sealed class CreateGatheringEvent : Event {
    object NavigationPopEvent: com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent()
    object GoToPrevPage: com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent()

    data class GoToHostRecruitment(val plubbingId: Int): CreateGatheringEvent()

    object GoToHome: CreateGatheringEvent()
}