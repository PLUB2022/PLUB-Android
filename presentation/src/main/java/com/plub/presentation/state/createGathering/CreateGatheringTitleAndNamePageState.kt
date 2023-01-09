package com.plub.presentation.state.createGathering

import com.plub.presentation.state.PageState

data class CreateGatheringTitleAndNamePageState(
    val introductionTitle: String = "",
    val gatheringName: String = ""
) : PageState {
    val isNextButtonEnabled = introductionTitle.isNotBlank() && gatheringName.isNotBlank()
}