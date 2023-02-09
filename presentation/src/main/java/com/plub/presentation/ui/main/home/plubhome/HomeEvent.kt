package com.plub.presentation.ui.main.home.plubhome

import com.plub.presentation.ui.Event

sealed class HomeEvent : Event {
    object GoToSearch : HomeEvent()
    object GoToBookMark : HomeEvent()
    object GoToRegisterInterest : HomeEvent()
    data class GoToCategoryGathering(val categoryId : Int, val categoryName : String) : HomeEvent()
    data class GoToRecruitment(val plubbingId : Int) : HomeEvent()
}
