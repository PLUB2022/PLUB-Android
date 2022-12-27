package com.plub.domain.model.state

import java.io.File

data class CreateGatheringGoalAndIntroduceAndPicturePageState(
    val gatheringGoal: String = "",
    val gatheringIntroduce: String = "",
    val gatheringImage: File? = null
) : PageState {
    val isNextButtonEnabled = gatheringGoal.isNotBlank() && gatheringIntroduce.isNotBlank()
}