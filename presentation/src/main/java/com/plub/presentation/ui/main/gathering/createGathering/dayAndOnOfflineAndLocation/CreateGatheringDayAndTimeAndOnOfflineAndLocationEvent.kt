package com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation

import com.plub.presentation.event.Event

sealed class CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent : Event {
    object ShowBottomSheetSearchLocation: CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent()
    object ShowTimePickerDialog: CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent()
}