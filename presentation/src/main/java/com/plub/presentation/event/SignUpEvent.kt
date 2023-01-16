package com.plub.presentation.event

sealed class SignUpEvent : Event {
    object NavigationPopEvent : SignUpEvent()
    data class ShowSignUpErrorDialog(val string: String) : SignUpEvent()
    object GoToWelcome : SignUpEvent()
}