package com.plub.presentation.ui.createGathering.gatheringTitleAndName

import com.plub.presentation.state.PageState

data class CreateGatheringTitleAndNamePageState(
    val introductionTitle: String = "",
    val gatheringName: String = ""
) : PageState {
    val isNextButtonEnabled = introductionTitle.isNotBlank() && gatheringName.isNotBlank()
}