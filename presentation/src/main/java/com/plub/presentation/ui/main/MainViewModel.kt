package com.plub.presentation.ui.main

import android.content.Intent
import android.view.MenuItem
import com.plub.domain.model.enums.BottomNavigationItemType
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.NavigationBundle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {
    private val fragmentStatusBarColorWhite = listOf(R.id.plubingAddScheduleFragment)

    fun onSelectedBottomNavigationMenu(fragmentId: Int) {
        val idx = getBottomMenuIndex(fragmentId)
        idx?.let {
            emitEventFlow(MainEvent.ShowBottomNavigationBadge(it))
        }
    }

    fun onDestinationChanged(fragmentId: Int) {
        val isBottomNav = isBottomNavigationFragment(fragmentId)
        emitEventFlow(MainEvent.BottomNavigationVisibility(isBottomNav))

        val statusBarColor = getStatusBarColor(fragmentId)
        emitEventFlow(MainEvent.ChangeStatusBarColor(statusBarColor))
    }

    private fun getBottomMenuIndex(fragmentId: Int): Int? {
        return when (fragmentId) {
            R.id.menu_navigation_main -> {
                BottomNavigationItemType.MAIN.idx
            }

            R.id.menu_navigation_gathering -> {
                BottomNavigationItemType.GATHERING.idx
            }

            R.id.menu_navigation_noti -> {
                BottomNavigationItemType.NOTI.idx
            }

            R.id.menu_navigation_profile -> {
                BottomNavigationItemType.PROFILE.idx
            }

            else -> null
        }
    }

    private fun isBottomNavigationFragment(fragmentId: Int): Boolean {
        return getBottomMenuIndex(fragmentId) != null
    }

    private fun getStatusBarColor(fragmentId: Int): Int {
        return when(fragmentId) {
            in fragmentStatusBarColorWhite -> { R.color.white }
            else -> { R.color.color_f5f3f6 }
        }
    }

    fun emitPopBackStackEventIfCurrentIdSameDestination(currentId: Int?, destination: Int) {
        if(currentId == destination)
            emitEventFlow(MainEvent.PopBackStack)
    }

    fun emitNavigate(navigationBundle: NavigationBundle) {
        emitEventFlow(MainEvent.Navigate(navigationBundle))
    }

    fun emitProcessIntent(intent: Intent?) {
        emitEventFlow(MainEvent.ProcessIntent(intent))
    }
}
