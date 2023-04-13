package com.plub.presentation.ui.main

import com.plub.presentation.ui.Event

sealed class MainEvent : Event {
    data class ShowBottomNavigationBadge(val index:Int) : MainEvent()
    data class BottomNavigationVisibility(val isVisible:Boolean) : MainEvent()

    data class ChangeStatusBarColor(val colorId: Int): MainEvent()
}
