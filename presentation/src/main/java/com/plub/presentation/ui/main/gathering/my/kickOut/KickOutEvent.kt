package com.plub.presentation.ui.main.gathering.my.kickOut

import com.plub.presentation.ui.Event

sealed class KickOutEvent : Event {
    object GoToBack: KickOutEvent()
}