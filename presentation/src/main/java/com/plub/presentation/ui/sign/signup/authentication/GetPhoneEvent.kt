package com.plub.presentation.ui.sign.signup.authentication

import com.plub.presentation.ui.Event

sealed class GetPhoneEvent : Event {
    object ShowBottomSheet : GetPhoneEvent()
    object CertificationSuccess : GetPhoneEvent()
    object EditTextNorMalColor : GetPhoneEvent()
    object MoveToNext : GetPhoneEvent()
    object TimerStart : GetPhoneEvent()
}