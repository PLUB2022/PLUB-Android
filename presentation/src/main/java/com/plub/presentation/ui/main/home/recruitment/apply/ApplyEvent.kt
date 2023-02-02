package com.plub.presentation.ui.main.home.recruitment.apply

import com.plub.presentation.ui.Event

sealed class ApplyEvent : Event {
    object BackPage : ApplyEvent()
    object ShowSuccessDialog : ApplyEvent()
}
