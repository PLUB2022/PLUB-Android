package com.plub.domain.model.state

data class CreateGatheringTitleAndNamePageState(
    val introductionTitle: String = "",
    val gatheringName: String = ""
) : PageState {
    val isNextButtonEnabled
        get() = introductionTitle.isNotBlank() && gatheringName.isNotBlank()
}