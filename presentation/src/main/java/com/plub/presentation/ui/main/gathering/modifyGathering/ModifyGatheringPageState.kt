package com.plub.presentation.ui.main.gathering.modifyGathering

import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.main.gathering.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.main.gathering.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestionPageState
import com.plub.presentation.ui.main.gathering.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState

data class ModifyGatheringPageState(
    val temp: String = ""
) : PageState