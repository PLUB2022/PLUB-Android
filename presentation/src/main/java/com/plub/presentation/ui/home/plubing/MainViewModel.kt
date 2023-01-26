package com.plub.presentation.ui.home.plubing

import com.plub.domain.model.enums.BottomNavigationItemType
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.MainEvent
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {

    private val bottomNavigationFragmentIds = listOf(
        R.id.menu_navigation_main,
        R.id.menu_navigation_gathering,
        R.id.menu_navigation_noti,
        R.id.menu_navigation_profile
    )

    fun onSelectedBottomNavigationMenu(fragmentId: Int) {
        val idx = getMenuIndex(fragmentId)
        emitEventFlow(MainEvent.ShowBottomNavigationBadge(idx))

        val isBottomNav = isBottomNavigationFragment(fragmentId)
        emitEventFlow(MainEvent.BottomNavigationVisibility(isBottomNav))
    }

    private fun getMenuIndex(fragmentId: Int): Int {
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

            else -> throw IllegalAccessException()
        }
    }

    private fun isBottomNavigationFragment(fragmentId: Int): Boolean {
        return fragmentId in bottomNavigationFragmentIds
    }
}
