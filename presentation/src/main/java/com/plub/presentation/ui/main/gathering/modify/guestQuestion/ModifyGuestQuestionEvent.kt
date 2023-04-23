package com.plub.presentation.ui.main.gathering.modify.guestQuestion

import com.plub.presentation.ui.Event

sealed class ModifyGuestQuestionEvent : Event {
    data class ShowBottomSheetDeleteQuestion(val size: Int, val position: Int) : ModifyGuestQuestionEvent()
    object PerformClickNoQuestionRadioButton: ModifyGuestQuestionEvent()
}