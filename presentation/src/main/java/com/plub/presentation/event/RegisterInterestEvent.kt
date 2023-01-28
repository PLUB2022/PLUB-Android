package com.plub.presentation.event

sealed class RegisterInterestEvent : Event{
    object BackPage : RegisterInterestEvent()
}
