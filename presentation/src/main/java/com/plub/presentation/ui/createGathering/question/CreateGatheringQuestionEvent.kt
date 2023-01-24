package com.plub.presentation.ui.createGathering.question

import com.plub.presentation.event.Event
import com.plub.presentation.state.PageState

sealed class CreateGatheringQuestionEvent : Event {
    data class ShowBottomSheetDeleteQuestion(val size: Int, val position: Int) : CreateGatheringQuestionEvent()
    object PerformClickNoQuestionRadioButton: CreateGatheringQuestionEvent()
}