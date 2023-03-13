package com.plub.presentation.ui.main.gathering.modifyGathering

import com.plub.presentation.ui.Event

sealed class ModifyGatheringEvent : Event {
    object InitViewPager: ModifyGatheringEvent()
}