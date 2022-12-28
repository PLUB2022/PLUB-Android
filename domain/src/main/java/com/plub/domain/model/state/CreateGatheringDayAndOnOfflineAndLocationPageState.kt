package com.plub.domain.model.state

data class CreateGatheringDayAndOnOfflineAndLocationPageState(
    val gatheringDays: List<String> = listOf(),
    val gatheringOnOffline: String = "",
    val gatheringLocation: String = "",
    val placePositionX: Double = 0.0,
    val placePositionY: Double = 0.0
) : PageState {
    val isNextButtonEnabled = gatheringDays.isNotEmpty() &&
            if(gatheringOnOffline == "ON")
                gatheringLocation.isNotBlank()
            else true
}