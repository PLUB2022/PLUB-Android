package com.plub.presentation.ui.main.home.registinterests

import com.plub.presentation.ui.Event

sealed class RegisterInterestEvent : Event {
    object BackPage : RegisterInterestEvent()
}
