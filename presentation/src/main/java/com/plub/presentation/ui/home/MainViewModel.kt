package com.plub.presentation.ui.home

import android.view.MenuItem
import com.plub.domain.model.enums.BottomNavigationItemType
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.LoginEvent
import com.plub.presentation.event.MainEvent
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {
    fun onSelectedBottomNavigationMenu(item:MenuItem) {
        val idx = getMenuIndex(item)
        emitEventFlow(MainEvent.ShowBottomNavigationBadge(idx))
    }

    private fun getMenuIndex(menuItem: MenuItem):Int {
        return when(menuItem.itemId) {
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

//    testPostHomeUseCase.invoke(HomePostRequestVo("testcode", false)).collect { state ->
//        when(state){
//            is UiState.Loading -> Log.d("테스트용", "로딩")
//            is UiState.Success -> Log.d("테스트용", "${state.successOrNull()!!.authCode}")
//            is UiState.Error -> Log.d("테스트용", "실패")
//        }
//    }
}
