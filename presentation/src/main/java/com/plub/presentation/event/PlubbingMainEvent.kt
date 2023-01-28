package com.plub.presentation.event

sealed class PlubbingMainEvent : Event{
    object GoToSearch : PlubbingMainEvent()
    object GoToBookMark : PlubbingMainEvent()
}
