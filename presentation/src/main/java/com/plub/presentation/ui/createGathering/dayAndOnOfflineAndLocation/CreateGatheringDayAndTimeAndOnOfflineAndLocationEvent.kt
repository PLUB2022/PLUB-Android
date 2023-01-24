package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import com.plub.presentation.event.Event
import com.plub.presentation.state.PageState

sealed class CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent : Event {
    object ShowBottomSheetSearchLocation: CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent()
    object ShowTimePickerDialog: CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent()
}