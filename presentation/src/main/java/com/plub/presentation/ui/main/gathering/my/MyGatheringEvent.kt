package com.plub.presentation.ui.main.gathering.my

import com.plub.presentation.ui.Event

sealed class MyGatheringEvent : Event {
    data class GoToPlubingMain(val plubingId: Int): MyGatheringEvent()
}