package com.plub.presentation.ui.main.profile.active

import com.plub.presentation.ui.Event

sealed class ActiveGatheringEvent : Event{
    object GoToBack : ActiveGatheringEvent()
    data class GoToDetailBoard(val feedId:Int) : ActiveGatheringEvent()
    data class GoToPlubbingMain(val plubbingId : Int) : ActiveGatheringEvent()
}
