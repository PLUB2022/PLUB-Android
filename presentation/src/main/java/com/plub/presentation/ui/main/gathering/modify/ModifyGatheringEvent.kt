package com.plub.presentation.ui.main.gathering.modify

import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionPageState
import com.plub.presentation.ui.main.gathering.modify.info.ModifyInfoPageState
import com.plub.presentation.ui.main.gathering.modify.recruit.ModifyRecruitPageState

sealed class ModifyGatheringEvent : Event {
    object GoToBack : ModifyGatheringEvent()

    data class GoToModifyQuestion(val data: ModifyGuestQuestionPageState) : ModifyGatheringEvent()

    data class GoToModifyRecruit(val data: ModifyRecruitPageState) : ModifyGatheringEvent()

    data class GoToModifyInfo(val data: ModifyInfoPageState) : ModifyGatheringEvent()
}