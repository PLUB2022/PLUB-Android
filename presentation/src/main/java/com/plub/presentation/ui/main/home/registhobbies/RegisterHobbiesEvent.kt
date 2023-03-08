package com.plub.presentation.ui.main.home.registhobbies

import com.plub.presentation.ui.Event

sealed class RegisterHobbiesEvent : Event {
    object BackPage : RegisterHobbiesEvent()
}
