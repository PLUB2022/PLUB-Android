package com.plub.presentation.ui.main.gathering.createGathering

import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.main.gathering.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.main.gathering.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestionPageState
import com.plub.presentation.ui.main.gathering.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState

data class CreateGatheringPageState(
    val currentPage: Int = 0,
    val selectPlubCategoryPageState: CreateGatheringSelectPlubCategoryPageState = CreateGatheringSelectPlubCategoryPageState(),
    val titleAndNamePageState: CreateGatheringTitleAndNamePageState = CreateGatheringTitleAndNamePageState(),
    val goalAndIntroduceAndPicturePageState: CreateGatheringGoalAndIntroduceAndPicturePageState = CreateGatheringGoalAndIntroduceAndPicturePageState(),
    val dayAndOnOfflineAndLocationPageState: CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState = CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState(),
    val peopleNumberPageState: CreateGatheringPeopleNumberPageState = CreateGatheringPeopleNumberPageState(),
    val questionPageState: CreateGatheringQuestionPageState = CreateGatheringQuestionPageState()
) : PageState