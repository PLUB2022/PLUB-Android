package com.plub.presentation.ui.main.home.profile.active

import com.plub.presentation.ui.Event

sealed class ActiveGatheringEvent : Event{
    data class GoToDetailBoard(val feedId:Int) : ActiveGatheringEvent()
}
