package com.plub.presentation.ui.main.gathering.create.gatheringTitleAndName

import com.plub.presentation.ui.PageState

data class CreateGatheringTitleAndNamePageState(
    val introductionTitle: String = "",
    val gatheringName: String = ""
) : PageState {
    val isNextButtonEnabled = introductionTitle.isNotBlank() && gatheringName.isNotBlank()
}