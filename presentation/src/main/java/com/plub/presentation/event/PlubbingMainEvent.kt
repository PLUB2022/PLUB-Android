package com.plub.presentation.event

import com.plub.presentation.ui.Event

sealed class PlubbingMainEvent : Event {
    object GoToSearch : PlubbingMainEvent()
    object GoToBookMark : PlubbingMainEvent()
}
