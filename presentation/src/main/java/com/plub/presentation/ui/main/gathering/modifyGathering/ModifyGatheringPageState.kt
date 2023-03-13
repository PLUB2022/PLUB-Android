package com.plub.presentation.ui.main.gathering.modifyGathering

import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.modifyGathering.guestQuestion.ModifyGuestQuestionPageState
import com.plub.presentation.ui.main.gathering.modifyGathering.recruit.ModifyRecruitPageState

data class ModifyGatheringPageState(
    val modifyRecruitPageState: ModifyRecruitPageState = ModifyRecruitPageState(),
    val modifyGuestQuestionPageState: ModifyGuestQuestionPageState = ModifyGuestQuestionPageState()
) : PageState