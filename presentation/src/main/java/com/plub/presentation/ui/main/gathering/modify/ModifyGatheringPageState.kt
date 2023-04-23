package com.plub.presentation.ui.main.gathering.modify

import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionPageState
import com.plub.presentation.ui.main.gathering.modify.recruit.ModifyRecruitPageState

data class ModifyGatheringPageState(
    val modifyRecruitPageState: ModifyRecruitPageState = ModifyRecruitPageState(),
    val modifyGuestQuestionPageState: ModifyGuestQuestionPageState = ModifyGuestQuestionPageState()
) : PageState