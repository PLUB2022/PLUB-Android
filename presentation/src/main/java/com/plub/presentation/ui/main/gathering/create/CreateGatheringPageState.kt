package com.plub.presentation.ui.main.gathering.create

import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.main.gathering.create.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.main.gathering.create.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.main.gathering.create.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.main.gathering.create.question.CreateGatheringQuestionPageState
import com.plub.presentation.ui.main.gathering.create.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState

data class CreateGatheringPageState(
    val plubbingId: Int = 0,
    val currentPage: Int = 0,
    val selectPlubCategoryPageState: CreateGatheringSelectPlubCategoryPageState = CreateGatheringSelectPlubCategoryPageState(),
    val titleAndNamePageState: CreateGatheringTitleAndNamePageState = CreateGatheringTitleAndNamePageState(),
    val goalAndIntroduceAndPicturePageState: CreateGatheringGoalAndIntroduceAndPicturePageState = CreateGatheringGoalAndIntroduceAndPicturePageState(),
    val dayAndOnOfflineAndLocationPageState: CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState = CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState(),
    val peopleNumberPageState: CreateGatheringPeopleNumberPageState = CreateGatheringPeopleNumberPageState(),
    val questionPageState: CreateGatheringQuestionPageState = CreateGatheringQuestionPageState()
) : PageState