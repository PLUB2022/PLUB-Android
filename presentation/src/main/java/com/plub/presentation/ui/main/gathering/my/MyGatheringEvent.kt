package com.plub.presentation.ui.main.gathering.my

import com.plub.presentation.ui.Event

sealed class MyGatheringEvent : Event {
    data class GoToPlubingMain(val plubingId: Int): MyGatheringEvent()

    object GoToCreateGathering: MyGatheringEvent()

    object GoToPlubingHome: MyGatheringEvent()

    data class GoToKickOut(val plubingId: Int): MyGatheringEvent()

    data class GoToModifyGathering(val plubingId: Int): MyGatheringEvent()
}