package com.plub.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import com.plub.presentation.ui.Event
import com.plub.presentation.util.NavigationBundle

sealed class MainEvent : Event {
    data class ShowBottomNavigationBadge(val index:Int) : MainEvent()
    data class BottomNavigationVisibility(val isVisible:Boolean) : MainEvent()

    data class ChangeStatusBarColor(val colorId: Int): MainEvent()

    data class Navigate(val navigationBundle: NavigationBundle): MainEvent()

    object PopBackStack: MainEvent()

    data class ProcessIntent(val intent: Intent?): MainEvent()
}
