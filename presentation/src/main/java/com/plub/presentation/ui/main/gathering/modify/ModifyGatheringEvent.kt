package com.plub.presentation.ui.main.gathering.modify

import com.plub.presentation.ui.Event

sealed class ModifyGatheringEvent : Event {
    object InitViewPager: ModifyGatheringEvent()
}