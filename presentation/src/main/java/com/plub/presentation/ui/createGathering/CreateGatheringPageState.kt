package com.plub.presentation.ui.createGathering

import com.plub.presentation.state.PageState
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.createGathering.question.CreateGatheringQuestionPageState
import com.plub.presentation.ui.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState

data class CreateGatheringPageState(
    val currentPage: Int = 0,
    val selectPlubCategoryPageState: CreateGatheringSelectPlubCategoryPageState = CreateGatheringSelectPlubCategoryPageState(),
    val titleAndNamePageState: CreateGatheringTitleAndNamePageState = CreateGatheringTitleAndNamePageState(),
    val goalAndIntroduceAndPicturePageState: CreateGatheringGoalAndIntroduceAndPicturePageState = CreateGatheringGoalAndIntroduceAndPicturePageState(),
    val dayAndOnOfflineAndLocationPageState: CreateGatheringDayAndOnOfflineAndLocationPageState = CreateGatheringDayAndOnOfflineAndLocationPageState(),
    val peopleNumberPageState: CreateGatheringPeopleNumberPageState = CreateGatheringPeopleNumberPageState(),
    val questionPageState: CreateGatheringQuestionPageState = CreateGatheringQuestionPageState()
) : PageState