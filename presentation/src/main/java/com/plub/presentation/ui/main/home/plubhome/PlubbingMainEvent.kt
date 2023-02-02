package com.plub.presentation.ui.main.home.plubhome

import com.plub.presentation.ui.Event

sealed class PlubbingMainEvent : Event {
    object GoToSearch : PlubbingMainEvent()
    object GoToBookMark : PlubbingMainEvent()
}
