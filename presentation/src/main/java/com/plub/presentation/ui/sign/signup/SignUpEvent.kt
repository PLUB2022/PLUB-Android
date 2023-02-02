package com.plub.presentation.ui.sign.signup

import com.plub.presentation.ui.Event

sealed class SignUpEvent : Event {
    object NavigationPopEvent : SignUpEvent()
    data class ShowSignUpErrorDialog(val string: String) : SignUpEvent()
    object GoToWelcome : SignUpEvent()
}