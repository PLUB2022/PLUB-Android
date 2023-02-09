package com.plub.presentation.ui.main.home.plubhome

import com.plub.presentation.ui.Event

sealed class PlubbingHomeEvent : Event {
    object GoToSearch : PlubbingHomeEvent()
    object GoToBookMark : PlubbingHomeEvent()
    data class GoToCategoryGathering(val categoryId : Int, val categoryName : String) : PlubbingHomeEvent()
}
