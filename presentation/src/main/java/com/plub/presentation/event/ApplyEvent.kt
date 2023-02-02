package com.plub.presentation.event

import com.plub.presentation.ui.Event

sealed class ApplyEvent : Event {
    object BackPage : ApplyEvent()
    object ShowSuccessDialog : ApplyEvent()
}
