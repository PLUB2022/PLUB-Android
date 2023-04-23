package com.plub.presentation.ui.main.gathering.create.question

import com.plub.presentation.ui.Event

sealed class CreateGatheringQuestionEvent : Event {
    data class ShowBottomSheetDeleteQuestion(val size: Int, val position: Int) : CreateGatheringQuestionEvent()
    object PerformClickNoQuestionRadioButton: CreateGatheringQuestionEvent()
}