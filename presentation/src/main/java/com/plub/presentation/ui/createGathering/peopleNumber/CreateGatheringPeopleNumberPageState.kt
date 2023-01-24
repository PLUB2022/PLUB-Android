package com.plub.presentation.ui.createGathering.peopleNumber

import com.plub.presentation.state.PageState

data class CreateGatheringPeopleNumberPageState(
    val seekBarProgress: Int = 0,
    val seekBarPositionX: Float = 0.0f
) : PageState {
    val peopleNumber = seekBarProgress + 4
}