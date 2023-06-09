package com.plub.presentation.ui.main.gathering.modify

import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionPageState

sealed class ModifyGatheringEvent : Event {
    object GoToBack : ModifyGatheringEvent()

    data class GoToModifyQuestion(val data: ModifyGuestQuestionPageState) : ModifyGatheringEvent()
}