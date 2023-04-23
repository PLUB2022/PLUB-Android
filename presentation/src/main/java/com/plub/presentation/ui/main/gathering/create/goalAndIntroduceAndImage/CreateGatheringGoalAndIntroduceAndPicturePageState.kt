package com.plub.presentation.ui.main.gathering.create.goalAndIntroduceAndImage

import com.plub.presentation.ui.PageState
import java.io.File

data class CreateGatheringGoalAndIntroduceAndPicturePageState(
    val gatheringGoal: String = "",
    val gatheringIntroduce: String = "",
    val gatheringImage: File? = null
) : PageState {
    val isNextButtonEnabled = gatheringGoal.isNotBlank() && gatheringIntroduce.isNotBlank()
}