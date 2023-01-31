package com.plub.presentation.event

sealed class ApplyEvent : Event{
    object BackPage : ApplyEvent()
    object ShowSuccessDialog : ApplyEvent()
}
