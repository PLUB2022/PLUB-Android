package com.plub.domain.model.state

data class CreateGatheringTitleAndNamePageState(
    val introductionTitle: String = "",
    val gatheringName: String = ""
) : PageState {
    val isNextButtonEnabled = introductionTitle.isNotBlank() && gatheringName.isNotBlank()
}