package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.graphics.Bitmap
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.main.gathering.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.main.gathering.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestionPageState
import com.plub.presentation.ui.main.gathering.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState

data class RecruitPageState(
    val title: String = "",
    val name: String = "",
    val goal: String = "",
    val introduce: String = "",
    val plubbingMainImgUrl: String = "",
    val tempPlubbingMainBitmap: Bitmap? = null
) : PageState