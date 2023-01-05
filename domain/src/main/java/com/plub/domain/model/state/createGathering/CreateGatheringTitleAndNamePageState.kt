package com.plub.domain.model.state.createGathering

import com.plub.domain.model.state.PageState

data class CreateGatheringTitleAndNamePageState(
    val introductionTitle: String = "",
    val gatheringName: String = ""
) : PageState {
    val isNextButtonEnabled = introductionTitle.isNotBlank() && gatheringName.isNotBlank()
}