package com.plub.presentation.ui.main

import android.view.MenuItem
import com.plub.domain.model.enums.BottomNavigationItemType
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.MainEvent
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {

    fun onSelectedBottomNavigationMenu(item: MenuItem) {
        val idx = getBottomMenuIndex(item.itemId)
        idx?.let {
            emitEventFlow(MainEvent.ShowBottomNavigationBadge(it))
        }
    }

    fun onDestinationChanged(fragmentId: Int) {
        val isBottomNav = isBottomNavigationFragment(fragmentId)
        emitEventFlow(MainEvent.BottomNavigationVisibility(isBottomNav))
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
}
