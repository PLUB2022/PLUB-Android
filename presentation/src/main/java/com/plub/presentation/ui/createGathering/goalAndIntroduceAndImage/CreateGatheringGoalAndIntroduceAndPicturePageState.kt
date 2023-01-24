package com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage

import com.plub.presentation.state.PageState
import java.io.File

data class CreateGatheringGoalAndIntroduceAndPicturePageState(
    val gatheringGoal: String = "",
    val gatheringIntroduce: String = "",
    val gatheringImage: File? = null
) : PageState {
    val isNextButtonEnabled = gatheringGoal.isNotBlank() && gatheringIntroduce.isNotBlank()
}