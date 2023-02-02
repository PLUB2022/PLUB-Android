package com.plub.presentation.event

import com.plub.presentation.ui.Event

sealed class RegisterInterestEvent : Event {
    object BackPage : RegisterInterestEvent()
}
